package com.anseolab.lotty.view.main.search

import android.icu.util.LocaleData
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.interactors.dhlottery.FetchLotteryNumberUseCase
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.extensions.getDrwNum
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.main.around.AroundViewModelType
import com.anseolab.lotty.view.main.search.mapper.LotteryListStateMapper
import com.anseolab.lotty.view.model.LotteryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val fetchLotteryNumberUseCase: FetchLotteryNumberUseCase
) : ReactorViewModel<SearchViewModel.Action, SearchViewModel.Mutation, SearchViewModel.State>(
    stateHandle, schedulerProvider
), SearchViewModelType, SearchViewModelType.Input, SearchViewModelType.Output {

    override fun onDrwNoClick(drwNo: Long) =
        createAction(Action.DrwNoClick(drwNo))

    private val _lotteries: MutableLiveData<List<LotteryUiModel>> = MutableLiveData()
    override val lotteries: LiveData<List<LotteryUiModel>> get() = _lotteries

    private val _drwNum: MutableLiveData<Long> = MutableLiveData()
    override val drwNum: LiveData<Long> get() = _drwNum

    override val input: SearchViewModelType.Input = this
    override val output: SearchViewModelType.Output = this

    init {
        state.distinctUntilChanged(LotteryListStateMapper::diff)
            .map(LotteryListStateMapper::mapToView)
            .bind(_lotteries)
    }

    override fun transformAction(action: Observable<Action>): Observable<out Action> {
        return action.startWithItem(Action.FetchLotteryNumber)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            drwNum = defaultState?.drwNum ?: Date().getDrwNum(),
            lotteryTrigger = defaultState?.lotteryTrigger ?: false,
            expandedLotteries = defaultState?.expandedLotteries ?: setOf(Date().getDrwNum())
        )
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.FetchLotteryNumber -> {
                Observable.concat(
                    Observable.concat(
                        fetchLotteryNumber(currentState.drwNum),
                        fetchLotteryNumber(currentState.drwNum - 1),
                        fetchLotteryNumber(currentState.drwNum - 2),
                        fetchLotteryNumber(currentState.drwNum - 3),
                        ),
                    Observable.concat(
                        fetchLotteryNumber(currentState.drwNum - 4),
                        fetchLotteryNumber(currentState.drwNum - 5),
                        fetchLotteryNumber(currentState.drwNum - 6),
                        fetchLotteryNumber(currentState.drwNum - 7),
                    )
                )
            }

            is Action.DrwNoClick -> {
                Observable.just(Mutation.SetExpandedLotteries(action.drwNo))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.FetchLotteryNumberSuccess -> {
                state.copy(
                    drwNum = mutation.response.drwNo ?: 0,
                    lotteryTrigger = !currentState.lotteryTrigger,
                    lotteries = currentState.lotteries.apply {
                        add(mutation.response)
                    })
            }

            is Mutation.SetExpandedLotteries -> {
                state.copy(expandedLotteries = currentState.expandedLotteries.toHashSet().apply {
                    if(this.contains(mutation.drwNo)){
                        this.remove(mutation.drwNo)
                    } else {
                        this.add(mutation.drwNo)
                    }
                })
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        object FetchLotteryNumber : Action

        class DrwNoClick(val drwNo: Long): Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class FetchLotteryNumberSuccess(val response: Lottery) : Mutation

        class SetExpandedLotteries(val drwNo: Long): Mutation
    }

    private fun fetchLotteryNumber(drwNum: Long): Observable<Mutation> {
        return fetchLotteryNumberUseCase.execute(drwNum)
            .map<Mutation>(Mutation::FetchLotteryNumberSuccess)
            .toObservable()
            .onErrorResumeNext {
                Observable.empty()
            }
    }

    data class State(
        val drwNum: Long,
        val lotteryTrigger: Boolean,
        val lotteries: MutableList<Lottery> = mutableListOf(),
        val expandedLotteries: Set<Long>
    ) : ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.drwNum,
                this.lotteryTrigger,
                this.expandedLotteries
            )
        }

        @Parcelize
        data class SavedState(
            val drwNum: Long,
            val lotteryTrigger: Boolean,
            val expandedLotteries: Set<Long>
        ) : Parcelable
    }
}