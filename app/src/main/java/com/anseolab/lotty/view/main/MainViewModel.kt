package com.anseolab.lotty.view.main

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider
) : ReactorViewModel<MainViewModel.Action, MainViewModel.Mutation, MainViewModel.State>(
    stateHandle, schedulerProvider
), MainViewModelType, MainViewModelType.Input, MainViewModelType.Output {

    override fun onPageSelect(page: Int) =
        createAction(Action.SelectPage(page))

    private val _selectedPage: MutableLiveData<Int> = MutableLiveData()
    override val selectedPage: LiveData<Int> get() = _selectedPage

    override val input: MainViewModelType.Input = this
    override val output: MainViewModelType.Output = this

    init {
        state.select(State::selectedPage)
            .bind(_selectedPage)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            selectedPage = defaultState?.selectedPage ?: 0
        )
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when(action) {
            is Action.SelectPage -> {
                Observable.just(Mutation.SetSelectedPage(action.page))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when(mutation) {
            is Mutation.SetSelectedPage -> {
                state.copy(selectedPage = mutation.page)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        class SelectPage(val page: Int) : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetSelectedPage(val page: Int) : Mutation
    }

    data class State(
        val selectedPage: Int
    ) : ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.selectedPage
            )
        }

        @Parcelize
        data class SavedState(
            val selectedPage: Int
        ): Parcelable
    }
}