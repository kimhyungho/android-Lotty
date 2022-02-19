package com.anseolab.lotty.view.detail

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.interactors.dhlottery.FetchLotteryNumberUseCase
import com.anseolab.domain.interactors.dhlottery.FetchRecentLotteriesUseCase
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.detail.mapper.LotteryStateMapper
import com.anseolab.lotty.view.lifecycle.SingleLiveData
import com.anseolab.lotty.view.model.LotteryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val fetchLotteryNumberUseCase: FetchLotteryNumberUseCase,
    private val fetchRecentLotteriesUseCase: FetchRecentLotteriesUseCase
) : ReactorViewModel<DetailViewModel.Action, DetailViewModel.Mutation, DetailViewModel.State>(
    stateHandle, schedulerProvider
), DetailViewModelType, DetailViewModelType.Input, DetailViewModelType.Output {

    override fun fetchLottery(lottery: Lottery?) =
         createAction(Action.FetchLottery(lottery))

    override fun onWordTextChange(word: String) =
        createAction(Action.WordTextChange(word))

    override fun onEditorAction() =
        createAction(Action.EditorAction)

    private val _lottery: MutableLiveData<LotteryUiModel> = MutableLiveData()
    override val lottery: LiveData<LotteryUiModel> get() = _lottery

    private val _word: MutableLiveData<String> = MutableLiveData()
    override val word: LiveData<String> get() = _word

    private val _showNumberError: MutableLiveData<Unit> = SingleLiveData()
    override val showNumberError: LiveData<Unit> get() = _showNumberError

    override val input: DetailViewModelType.Input = this
    override val output: DetailViewModelType.Output = this

    init {
        state.distinctUntilChanged(LotteryStateMapper::diff)
            .map(LotteryStateMapper::mapToView)
            .bind(_lottery)

        state.select(State::word)
            .bind(_word)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            word = defaultState?.word ?: ""
        )
    }

    override fun transformAction(action: Observable<Action>): Observable<out Action> {
        return action.startWithItem(Action.FetchRecentLotteries)
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when(action) {
            is Action.FetchRecentLotteries -> {
                fetchRecentLotteriesUseCase.execute()
                    .map(Mutation::SetRecentLotteries)
                    .toObservable()
            }

            is Action.FetchLottery -> {
                Observable.just(Mutation.SetLottery(action.lottery))
            }

            is Action.WordTextChange -> {
                Observable.just(Mutation.SetWord(action.word))
            }

            is Action.EditorAction -> {
                try {
                    val drwNo = currentState.word.toLong()

                    fetchLotteryNumberUseCase.execute(drwNo)
                        .map(Mutation::SetLottery)
                        .toObservable()
                } catch (e: Exception) {
                    _showNumberError.value = Unit
                    Observable.empty()
                }
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when(mutation) {
            is Mutation.SetLottery -> {
                state.copy(lottery = mutation.lottery)
            }

            is Mutation.SetWord -> {
                state.copy(word = mutation.word)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        object FetchRecentLotteries : Action

        class FetchLottery(val lottery: Lottery?): Action

        class WordTextChange(val word: String): Action

        object EditorAction: Action
    }

    interface Mutation: ReactorViewModel.Mutation {
        class SetRecentLotteries(val response: List<Lottery>): Mutation

        class SetLottery(val lottery: Lottery?) : Mutation

        class SetWord(val word: String): Mutation
    }

    data class State(
        val recentLotteries: List<Lottery> = listOf(),
        val lottery: Lottery? = null,
        val word: String
    ): ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.word
            )
        }

        @Parcelize
        data class SavedState(
            val word: String
        ): Parcelable
    }
}