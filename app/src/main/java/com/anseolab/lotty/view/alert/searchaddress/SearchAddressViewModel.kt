package com.anseolab.lotty.view.alert.searchaddress

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.interactors.kakao.FetchAddressUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.alert.searchaddress.mapper.RecentAddressStateMapper
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.model.RecentAddressUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class SearchAddressViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val fetchAddressUseCase: FetchAddressUseCase
) : ReactorViewModel<SearchAddressViewModel.Action, SearchAddressViewModel.Mutation, SearchAddressViewModel.State>(
    stateHandle, schedulerProvider
), SearchAddressViewModelType, SearchAddressViewModelType.Input, SearchAddressViewModelType.Output {

    override fun onAddressTextChange(address: String) =
        createAction(Action.AddressTextChange(address))

    private val _recentAddresses: MutableLiveData<List<RecentAddressUiModel>> = MutableLiveData()
    override val recentAddresses: LiveData<List<RecentAddressUiModel>> get() = _recentAddresses

    private val _address: MutableLiveData<String> = MutableLiveData()
    override val address: LiveData<String> get() = _address

    override val input: SearchAddressViewModelType.Input = this
    override val output: SearchAddressViewModelType.Output = this

    init {
        state.select(State::address)
            .bind(_address)

        state.distinctUntilChanged(RecentAddressStateMapper::diff)
            .map(RecentAddressStateMapper::mapToView)
            .bind(_recentAddresses)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            address = defaultState?.address ?: ""
        )
    }

    override fun transformAction(action: Observable<Action>): Observable<out Action> {
        return action.startWithItem(Action.Init)
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.Init -> {
                fetchAddressUseCase.execute()
                    .map<Mutation>(Mutation::SetRecentAddresses)
                    .toObservable()
            }

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

            is Mutation.SetRecentAddresses -> {
                state.copy(recentAddresses = mutation.response)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        object Init : Action

        class AddressTextChange(val address: String) : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetRecentAddresses(val response: List<String>) : Mutation

        class SetAddress(val address: String) : Mutation
    }

    data class State(
        val recentAddresses: List<String> = listOf(),
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