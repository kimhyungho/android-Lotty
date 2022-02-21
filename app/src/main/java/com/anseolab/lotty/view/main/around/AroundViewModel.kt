package com.anseolab.lotty.view.main.around

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.interactors.naver.SearchUseCase
import com.anseolab.domain.model.Store
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class AroundViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val searchUseCase: SearchUseCase
) : ReactorViewModel<AroundViewModel.Action, AroundViewModel.Mutation, AroundViewModel.State>(
    stateHandle, schedulerProvider
), AroundViewModelType, AroundViewModelType.Input, AroundViewModelType.Output {
    override fun onCameraIdleChange(x: Double, y: Double) =
        createAction(Action.CameraIdleChange(x, y))

    private val _stores: MutableLiveData<List<Store>> = MutableLiveData()
    override val stores: LiveData<List<Store>> get() = _stores

    override val input: AroundViewModelType.Input = this
    override val output: AroundViewModelType.Output = this

    init {
        state.select(State::stores)
            .bind(_stores)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.CameraIdleChange -> {
                val params = SearchUseCase.Params(
                    query = "복권 판매점",
                    x = action.x,
                    y = action.y
                )

                searchUseCase.execute(params)
                    .map(Mutation::SearchStoreSuccess)
                    .toObservable()
                    .onErrorResumeNext {
                        Observable.empty()
                    }
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.SearchStoreSuccess -> {
                state.copy(stores = mutation.response)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        class CameraIdleChange(val x: Double, val y: Double) : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SearchStoreSuccess(val response: List<Store>) : Mutation
    }

    data class State(
        val stores: List<Store> = listOf()
    ) : ReactorViewModel.State
}