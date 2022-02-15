package com.anseolab.lotty.view.main.around

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class AroundViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider
) : ReactorViewModel<AroundViewModel.Action, AroundViewModel.Mutation, AroundViewModel.State>(
    stateHandle, schedulerProvider
), AroundViewModelType, AroundViewModelType.Input, AroundViewModelType.Output {

    override val input: AroundViewModelType.Input = this
    override val output: AroundViewModelType.Output = this

    init {

    }

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when(action) {

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when(mutation) {

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
    }

    interface Mutation : ReactorViewModel.Mutation {
    }

    data class State(
        val name: Int = 0
    ) : ReactorViewModel.State
}