package com.anseolab.lotty.view.main.home

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.R
import com.anseolab.lotty.providers.resource.ResourceProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.main.home.mapper.SlotMachineStateMapper
import com.anseolab.lotty.view.model.SlotMachineUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val resourceProvider: ResourceProvider,
    private val slotMachineStateMapper: SlotMachineStateMapper
) : ReactorViewModel<HomeViewModel.Action, HomeViewModel.Mutation, HomeViewModel.State>(
    stateHandle, schedulerProvider
), HomeViewModelType, HomeViewModelType.Input, HomeViewModelType.Output {

    override fun onPageStateChange(lastItem: Int) =
        createAction(Action.PageScrollStateChange(lastItem))

    private val _drawables: MutableLiveData<List<SlotMachineUiModel>> = MutableLiveData()
    override val drawables: LiveData<List<SlotMachineUiModel>> get() = _drawables

    private val _currentItem: MutableLiveData<Int> = MutableLiveData()
    override val currentItem: LiveData<Int> get() = _currentItem

    private val _lastItem: MutableLiveData<Int> = MutableLiveData()
    override val lastItem: LiveData<Int> get() = _lastItem

    override val input: HomeViewModelType.Input = this
    override val output: HomeViewModelType.Output = this

    init {
        state.select(State::lastItem)
            .unwrapOptional()
            .bind(_lastItem)

        state.distinctUntilChanged(slotMachineStateMapper::diff)
            .map(slotMachineStateMapper::mapToView)
            .bind(_drawables)
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.PageScrollStateChange -> {
                Observable.just(Mutation.SetLastItem(action.lastItem))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.SetLastItem -> {
                state.copy(lastItem = mutation.lastItem)
            }

            else -> state
        }
    }

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    interface Action : ReactorViewModel.Action {
        class PageScrollStateChange(val lastItem: Int) : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetLastItem(val lastItem: Int) : Mutation
    }

    data class State(
        val drawables: List<Pair<Int,Int>> = listOf(
            Pair(0, R.drawable.ic_around_24x24),
            Pair(1,R.drawable.ic_arrow_down_24x24),
            Pair(2,R.drawable.ic_coin_36x36),
            Pair(3, R.drawable.ic_around_24x24),
            Pair(4,R.drawable.ic_arrow_down_24x24),
            Pair(5,R.drawable.ic_coin_36x36),
            Pair(6, R.drawable.ic_around_24x24),
            Pair(7,R.drawable.ic_arrow_down_24x24),
            Pair(8,R.drawable.ic_coin_36x36)
        ),
        val lastItem: Int? = null
    ) : ReactorViewModel.State {
//        override fun toParcelable(): Parcelable? {
//            return SavedState(
//                this.lastItem
//            )
//        }
//
//        @Parcelize
//        data class SavedState(
//            val lastItem: Int
//        ): Parcelable
    }
}