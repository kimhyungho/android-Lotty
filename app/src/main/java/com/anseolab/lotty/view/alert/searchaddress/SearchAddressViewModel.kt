package com.anseolab.lotty.view.alert.searchaddress

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
class SearchAddressViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider
) : ReactorViewModel<SearchAddressViewModel.Action, SearchAddressViewModel.Mutation, SearchAddressViewModel.State>(
    stateHandle, schedulerProvider
), SearchAddressViewModelType, SearchAddressViewModelType.Input, SearchAddressViewModelType.Output {

    override fun onAddressTextChange(address: String) =
        createAction(Action.AddressTextChange(address))

    private val _address: MutableLiveData<String> = MutableLiveData()
    override val address: LiveData<String> get() = _address

    override val input: SearchAddressViewModelType.Input = this
    override val output: SearchAddressViewModelType.Output = this

    init {
        state.select(State::address)
            .bind(_address)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            address = defaultState?.address ?: ""
        )
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.AddressTextChange -> {
                Observable.just(Mutation.SetAddress(action.address))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.SetAddress -> {
                state.copy(address = mutation.address)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        class AddressTextChange(val address: String) : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetAddress(val address: String) : Mutation
    }

    data class State(
        val address: String
    ) : ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.address
            )
        }

        @Parcelize
        data class SavedState(
            val address: String
        ) : Parcelable
    }
}