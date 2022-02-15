package com.anseolab.lotty.view.base

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.anseolab.domain.providers.SchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

abstract class ReactorViewModel<Action : ReactorViewModel.Action, Mutation : ReactorViewModel.Mutation, State : ReactorViewModel.State>(
    protected val stateHandle: SavedStateHandle,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    private val _action: PublishSubject<Action> = PublishSubject.create()
    protected val action: Observable<Action> = _action.hide()

    protected open val scheduler = schedulerProvider.ui

    protected val initialState: State by lazy {
        createInitialState(stateHandle.get(KEY_STATE))
    }

    protected val state: Observable<State> by lazy {
        createReactorStream()
    }

    private var _currentState: State? = null
        set(value) {
            field = value
            if (value != null) {
                stateHandle.set(KEY_STATE, value.toParcelable())
            }
        }

    val currentState: State
        get() {
            return _currentState ?: this.initialState
        }

    protected abstract fun createInitialState(savedState: Parcelable?): State

    private fun createReactorStream(): Observable<State> {
        val transformedAction = this.transformAction(action.observeOn(this.scheduler))
        val actionToMutation = transformedAction
            .flatMap { action ->
                this.mutate(action)
                    .onErrorResumeNext { throwable: Throwable ->
                        throwable.printStackTrace()
                        Observable.empty()
                    }
            }

        val transformedMutation = this.transformMutation(actionToMutation)
        val mutationToState = transformedMutation
            .scan(this.initialState) { state, mutation ->
                this.reduce(state, mutation)
            }
            .onErrorResumeNext { throwable: Throwable ->
                throwable.printStackTrace()
                Observable.empty()
            }
            .startWithItem(this.initialState)
        val transformedState = this.transformState(mutationToState)
            .doOnNext { state -> this._currentState = state }
            .replay(1)
        transformedState.connect()
            .let(compositeDisposable::add)

        return transformedState
    }

    protected open fun transformAction(action: Observable<Action>): Observable<out Action> {
        return action
    }

    protected open fun transformMutation(mutation: Observable<Mutation>): Observable<out Mutation> {
        return mutation
    }

    protected open fun transformState(state: Observable<State>): Observable<State> {
        return state
    }

    protected open fun mutate(action: Action): Observable<out Mutation> {
        return Observable.empty()
    }

    protected open fun reduce(state: State, mutation: Mutation): State {
        return state
    }

    protected fun createAction(action: Action) {
        this._action.onNext(action)
    }

    protected inline fun <reified T : Action> Observable<in T>.filterAction(): Observable<out T> {
        return this.filter { action -> action is T }
            .map { state -> state as T }
    }

    protected inline fun <reified T : State, K : Any> Observable<T>.select(
        crossinline selector: (state: T) -> K
    ): Observable<K> {
        return this.distinctUntilChanged { state: T -> selector.invoke(state) }
            .map { state: T -> selector.invoke(state) }
    }

    @JvmName(name = "selectOptional")
    protected inline fun <reified T : State, K : Any> Observable<T>.select(
        crossinline selector: (state: T) -> K?
    ): Observable<Optional<K>> {
        return this.distinctUntilChanged { state: T -> selector.invoke(state) }
            .map { state: T -> Optional.ofNullable(selector.invoke(state)) }
    }

    protected fun <T : Any> Observable<T>.bind(to: MutableLiveData<T>) {
        this.observeOn(schedulerProvider.ui)
            .subscribe(to::setValue) {
                it.printStackTrace()
            }
            .let(compositeDisposable::add)
    }

    protected fun <T: Any> Observable<Optional<T>>.unwrapOptional(): Observable<T> {
        return this.compose{ upstream ->
            upstream.filter(Optional<T>::isPresent).map(Optional<T>::get)
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    interface Action

    interface Mutation

    interface State {
        fun toParcelable(): Parcelable? = null
    }

    companion object {
        private const val KEY_STATE = "keyState"
    }


}