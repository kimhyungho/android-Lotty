package com.anseolab.lotty.view.main.around

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.interactors.kakao.KakaoSearchUseCase
import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.model.exeption.LottyException
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.mapper.ExceptionMapper
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.lifecycle.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class AroundViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val kakaoSearchUseCase: KakaoSearchUseCase
) : ReactorViewModel<AroundViewModel.Action, AroundViewModel.Mutation, AroundViewModel.State>(
    stateHandle, schedulerProvider
), AroundViewModelType, AroundViewModelType.Input, AroundViewModelType.Output {
    override fun onCameraIdleChange(x: Double, y: Double) =
        createAction(Action.CameraIdleChange(x, y))

    override fun onMarkerClick(store: KakaoStore) =
        createAction(Action.MarkerClick(store))

    override fun onSearchButtonClick(address: String) =
        createAction(Action.SearchButtonClick(address))

    override fun onMapClick() =
        createAction(Action.MapClick)

    private val _stores: MutableLiveData<List<KakaoStore>> = MutableLiveData()
    override val stores: LiveData<List<KakaoStore>> get() = _stores

    private val _store: MutableLiveData<KakaoStore> = MutableLiveData()
    override val store: LiveData<KakaoStore> get() = _store

    private val _showStoreInfo: MutableLiveData<Boolean> = MutableLiveData()
    override val showStoreInfo: LiveData<Boolean> get() = _showStoreInfo

    private val _showStoreLocation: MutableLiveData<Boolean> = MutableLiveData()
    override val showStoreLocation: LiveData<Boolean> get() = _showStoreLocation

    private val _showApiExceeded: MutableLiveData<Boolean> = MutableLiveData()
    override val showApiExceeded: LiveData<Boolean> get() = _showApiExceeded

    private val _showError: MutableLiveData<String> = SingleLiveData()
    override val showError: LiveData<String> get() = _showError

    override val input: AroundViewModelType.Input = this
    override val output: AroundViewModelType.Output = this

    init {
        state.select(State::stores)
            .bind(_stores)

        state.select(State::store)
            .unwrapOptional()
            .bind(_store)

        state.select(State::showStoreInfo)
            .bind(_showStoreInfo)

        state.select(State::showStoreLocation)
            .bind(_showStoreLocation)

        state.select(State::throwable)
            .unwrapOptional()
            .map(this::isApiExceedError)
            .bind(_showApiExceeded)

        state.select(State::throwable)
            .unwrapOptional()
            .map(ExceptionMapper::mapToView)
            .bind(_showError)
    }

    private fun isApiExceedError(throwable: Throwable): Boolean {
        return throwable is LottyException && throwable.code == LottyException.OVER_SEARCH_REQUEST
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState = savedState as? State.SavedState
        return State(
            showStoreInfo = defaultState?.showStoreInfo ?: false
        )
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.CameraIdleChange -> {
                val params = KakaoSearchUseCase.Params(
                    query = "복권 판매점",
                    x = action.x,
                    y = action.y,
                    type = "normal"
                )

                kakaoSearchUseCase.execute(params)
                    .map<Mutation>(Mutation::SearchStoreSuccess)
                    .toObservable()
                    .onErrorResumeNext {
                        Observable.just(Mutation.SetThrowable(it))
                    }
            }

            is Action.SearchButtonClick -> {
                val params = KakaoSearchUseCase.Params(
                    query = "${action.address} 복권 판매점",
                    x = 0.0,
                    y = 0.0,
                    type = "search"
                )

                kakaoSearchUseCase.execute(params)
                    .map<Mutation>(Mutation::SearchWithDialogStoreSuccess)
                    .toObservable()
                    .onErrorResumeNext {
                        Observable.just(Mutation.SetThrowable(it))
                    }
            }

            is Action.MarkerClick -> {
                Observable.just(Mutation.SetStore(action.store))
            }

            is Action.MapClick -> {
                Observable.just(Mutation.SetStore(null))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.SearchStoreSuccess -> {
                state.copy(stores = mutation.response, showStoreLocation = false)
            }

            is Mutation.SearchWithDialogStoreSuccess -> {
                state.copy(stores = mutation.response, showStoreLocation = true)
            }

            is Mutation.SetStore -> {
                state.copy(store = mutation.store, showStoreInfo = mutation.store != null)
            }

            is Mutation.SetThrowable -> {
                state.copy(throwable = mutation.throwable)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        class CameraIdleChange(val x: Double, val y: Double) : Action

        class MarkerClick(val store: KakaoStore) : Action

        class SearchButtonClick(val address: String): Action

        object MapClick : Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SearchStoreSuccess(val response: List<KakaoStore>) : Mutation

        class SearchWithDialogStoreSuccess(val response: List<KakaoStore>): Mutation

        class SetStore(val store: KakaoStore?) : Mutation

        class SetThrowable (val throwable: Throwable): Mutation
    }

    data class State(
        val stores: List<KakaoStore> = listOf(),
        val store: KakaoStore? = null,
        val showStoreInfo: Boolean,
        val showStoreLocation: Boolean = false,
        val throwable: Throwable? = null
    ) : ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.showStoreInfo
            )
        }

        @Parcelize
        data class SavedState(
            val showStoreInfo: Boolean
        ): Parcelable
    }
}