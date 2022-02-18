package com.anseolab.lotty.view.detail

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
class DetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider
) : ReactorViewModel<DetailViewModel.Action, DetailViewModel.Mutation, DetailViewModel.State>(
    stateHandle, schedulerProvider
), DetailViewModelType, DetailViewModelType.Input, DetailViewModelType.Output {

    override fun onWordTextChange(word: String) =
        createAction(Action.WordTextChange(word))

    private val _word: MutableLiveData<String> = MutableLiveData()
    override val word: LiveData<String> get() = _word

    override val input: DetailViewModelType.Input = this
    override val output: DetailViewModelType.Output = this

    init {
        state.select(State::word)
            .bind(_word)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            word = defaultState?.word ?: ""
        )
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when(action) {
            is Action.WordTextChange -> {
                Observable.just(Mutation.SetWord(action.word))
            }
            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when(mutation) {
            is Mutation.SetWord -> {
                state.copy(word = mutation.word)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        class WordTextChange(val word: String): Action
    }

    interface Mutation: ReactorViewModel.Mutation {
        class SetWord(val word: String): Mutation
    }

    data class State(
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