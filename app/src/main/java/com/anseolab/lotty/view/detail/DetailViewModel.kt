package com.anseolab.lotty.view.detail

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.data.mapper.LotteriesMapper
import com.anseolab.domain.interactors.dhlottery.*
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.detail.mapper.LotteryStateMapper
import com.anseolab.lotty.view.detail.mapper.RecentLotteriesStateMapper
import com.anseolab.lotty.view.lifecycle.SingleLiveData
import com.anseolab.lotty.view.model.LotteryUiModel
import com.anseolab.lotty.view.model.RecentLotteryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val searchLotteryUseCase: SearchLotteryUseCase,
    private val fetchRecentLotteriesUseCase: FetchRecentLotteriesUseCase,
    private val deleteLotteryUseCase: DeleteLotteryUseCase,
    private val clearLotteriesUseCase: ClearLotteriesUseCase
) : ReactorViewModel<DetailViewModel.Action, DetailViewModel.Mutation, DetailViewModel.State>(
    stateHandle, schedulerProvider
), DetailViewModelType, DetailViewModelType.Input, DetailViewModelType.Output {

    override fun fetchLottery(drwNo: Long?) =
        createAction(Action.FetchLottery(drwNo))

    override fun onWordTextChange(word: String) =
        createAction(Action.WordTextChange(word))

    override fun onEditorAction() =
        createAction(Action.EditorAction)

    override fun onClearButtonClick() =
        createAction(Action.ClearButtonClick)

    override fun onDrwNoItemClick(drwNo: Long) =
        createAction(Action.DrwNoItemClick(drwNo))

    override fun onDeleteItemClick(drwNo: Long) =
        createAction(Action.DeleteItemClick(drwNo))

    override fun onTextClearButtonClick() =
        createAction(Action.WordTextChange(""))

    private val _lottery: MutableLiveData<LotteryUiModel> = MutableLiveData()
    override val lottery: LiveData<LotteryUiModel> get() = _lottery

    private val _recentLotteries: MutableLiveData<List<RecentLotteryUiModel>> = MutableLiveData()
    override val recentLotteries: LiveData<List<RecentLotteryUiModel>> get() = _recentLotteries

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

        state.distinctUntilChanged(RecentLotteriesStateMapper::diff)
            .map(RecentLotteriesStateMapper::mapToView)
            .bind(_recentLotteries)

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
        return when (action) {
            is Action.FetchRecentLotteries -> {
                fetchRecentLotteriesUseCase.execute()
                    .map(Mutation::SetRecentLotteries)
                    .toObservable()
            }

            is Action.WordTextChange -> {
                Observable.just(Mutation.SetWord(action.word))
            }

            is Action.DeleteItemClick -> {
                deleteLotteryUseCase.execute(action.drwNo)
                    .toObservable()
            }

            is Action.ClearButtonClick -> {
                clearLotteriesUseCase.execute()
                    .toObservable()
            }

            is Action.DrwNoItemClick -> {
                searchLotteryUseCase.execute(action.drwNo)
                    .map(Mutation::SetLottery)
                    .toObservable()
            }

            is Action.FetchLottery -> {
                val drwNo = action.drwNo
                if (drwNo != null) {
                    searchLotteryUseCase.execute(drwNo)
                        .map(Mutation::SetLottery)
                        .toObservable()
                } else {
                    Observable.empty()
                }
            }

            is Action.EditorAction -> {
                try {
                    val drwNo = currentState.word.toLong()
                    searchLotteryUseCase.execute(drwNo)
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
        return when (mutation) {
            is Mutation.SetRecentLotteries -> {
                state.copy(recentLotteries = mutation.response)
            }

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

        class FetchLottery(val drwNo: Long?) : Action

        class WordTextChange(val word: String) : Action

        object EditorAction : Action

        object ClearButtonClick: Action

        class DeleteItemClick(val drwNo: Long): Action

        class DrwNoItemClick(val drwNo: Long): Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetRecentLotteries(val response: List<Lottery>) : Mutation

        class SetLottery(val lottery: Lottery?) : Mutation

        class SetWord(val word: String) : Mutation
    }

    data class State(
        val recentLotteries: List<Lottery> = listOf(),
        val lottery: Lottery? = null,
        val word: String
    ) : ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.word
            )
        }

        @Parcelize
        data class SavedState(
            val word: String
        ) : Parcelable
    }
}