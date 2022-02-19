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

    override fun refresh() =
        createAction(Action.Refresh)

    override fun onScroll(drwNo: Long) =
        createAction(Action.Scroll(drwNo))

    override fun onDrwNoClick(drwNo: Long) =
        createAction(Action.DrwNoClick(drwNo))

    private val _lotteries: MutableLiveData<List<LotteryUiModel>> = MutableLiveData()
    override val lotteries: LiveData<List<LotteryUiModel>> get() = _lotteries

    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    override val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    override val input: SearchViewModelType.Input = this
    override val output: SearchViewModelType.Output = this

    init {
        state.distinctUntilChanged(LotteryListStateMapper::diff)
            .map(LotteryListStateMapper::mapToView)
            .bind(_lotteries)

        state.select(State::isRefreshing)
            .bind(_isRefreshing)

        state.select(State::isLoading)
            .bind(_isLoading)
    }



    override fun transformAction(action: Observable<Action>): Observable<out Action> {
        return action.startWithItem(Action.Refresh)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            lotteryTrigger = defaultState?.lotteryTrigger ?: false,
            expandedLotteries = defaultState?.expandedLotteries ?: setOf(0L, Date().getDrwNum()),
        )
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.Refresh -> {
                Observable.concat(
                    Observable.just(Mutation.SetRefreshing(true)),
                    Observable.just(Mutation.InitLotteryNumber),
                    fetchLotteriesNumber(Date().getDrwNum())
                )
            }

            is Action.Scroll -> {
                Observable.concat(
                    Observable.just(Mutation.SetLoading(true)),
                    fetchLotteriesNumber(action.drwNo)
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
            is Mutation.InitLotteryNumber -> {
                state.copy(
                    lotteryTrigger = !currentState.lotteryTrigger,
                    lotteries = mutableListOf()
                )
            }

            is Mutation.FetchLotteryNumberSuccess -> {
                state.copy(
                    lotteryTrigger = !currentState.lotteryTrigger,
                    lotteries = currentState.lotteries.apply {
                        add(mutation.response)
                    })
            }

            is Mutation.SetRefreshing -> {
                state.copy(isLoading = false, isRefreshing = mutation.isRefreshing)
            }

            is Mutation.SetLoading -> {
                state.copy(isLoading = mutation.isLoading)
            }

            is Mutation.SetExpandedLotteries -> {
                state.copy(expandedLotteries = currentState.expandedLotteries.toHashSet().apply {
                    if (this.contains(mutation.drwNo)) {
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
        object Refresh : Action

        class Scroll(val drwNo: Long): Action

        class DrwNoClick(val drwNo: Long) : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        object InitLotteryNumber: Mutation

        class FetchLotteryNumberSuccess(val response: Lottery) : Mutation

        class SetExpandedLotteries(val drwNo: Long) : Mutation

        class SetRefreshing(val isRefreshing: Boolean): Mutation

        class SetLoading(val isLoading: Boolean) : Mutation
    }

    private fun fetchLotteriesNumber(firstDrwNum: Long): Observable<Mutation> {
        return Observable.concat(
            Observable.concat(
                fetchLotteryNumber(firstDrwNum),
                fetchLotteryNumber(firstDrwNum - 1),
                fetchLotteryNumber(firstDrwNum - 2),
                fetchLotteryNumber(firstDrwNum - 3),
            ),
            Observable.concat(
                fetchLotteryNumber(firstDrwNum - 4),
                fetchLotteryNumber(firstDrwNum - 5),
                fetchLotteryNumber(firstDrwNum - 6),
                fetchLotteryNumber(firstDrwNum - 7),
            ),
            Observable.concat(
                fetchLotteryNumber(firstDrwNum - 8),
                fetchLotteryNumber(firstDrwNum - 9),
                fetchLotteryNumber(firstDrwNum - 10),
                fetchLotteryNumber(firstDrwNum - 11),
            ),
            Observable.just(Mutation.SetRefreshing(false))
        )
    }

    private fun fetchLotteryNumber(drwNum: Long): Observable<Mutation> {
        return if(drwNum > 0) {
            fetchLotteryNumberUseCase.execute(drwNum)
                .map<Mutation>(Mutation::FetchLotteryNumberSuccess)
                .toObservable()
                .onErrorResumeNext {
                    Observable.empty()
                }
        } else {
            Observable.empty()
        }
    }

    data class State(
        val lotteryTrigger: Boolean,
        val lotteries: MutableList<Lottery> = mutableListOf(),
        val expandedLotteries: Set<Long>,
        val isRefreshing: Boolean = false,
        val isLoading : Boolean = false
    ) : ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.lotteryTrigger,
                this.expandedLotteries,
            )
        }

        @Parcelize
        data class SavedState(
            val lotteryTrigger: Boolean,
            val expandedLotteries: Set<Long>,
        ) : Parcelable
    }
}