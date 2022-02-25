package com.anseolab.lotty.view.main.random

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.data.mapper.DrwtNoMapper
import com.anseolab.domain.model.DrwtNo
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.main.around.AroundViewModelType
import com.anseolab.lotty.view.main.random.mapper.DrwtNoStateMapper
import com.anseolab.lotty.view.model.DrwtNoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider
) : ReactorViewModel<RandomViewModel.Action, RandomViewModel.Mutation, RandomViewModel.State>(
    stateHandle, schedulerProvider
), RandomViewModelType, RandomViewModelType.Input, RandomViewModelType.Output {
    override fun onCreateButtonClick() =
        createAction(Action.CreateButtonClick)

    private val _drwtNo: MutableLiveData<DrwtNoUiModel> = MutableLiveData()
    override val drwtNo: LiveData<DrwtNoUiModel> get() = _drwtNo

    override val input: RandomViewModelType.Input = this
    override val output: RandomViewModelType.Output = this

    init {
        state.distinctUntilChanged(DrwtNoStateMapper::diff)
            .map(DrwtNoStateMapper::mapToView)
            .bind(_drwtNo)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when(action) {
            is Action.CreateButtonClick -> {
                Observable.just(Mutation.SetDrwtNo(createDrwtNo()))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when(mutation) {
            is Mutation.SetDrwtNo -> {
                state.copy(drwtNo = mutation.drwtNo)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        object CreateButtonClick: Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetDrwtNo(val drwtNo: DrwtNo): Mutation
    }

    private fun createDrwtNo(): DrwtNo {
        val range = (1..45)
        val drwtNo = HashSet<Int>()

        while (drwtNo.size < 7) {
            drwtNo.add(range.random())
        }

        val bnusNo = drwtNo.last()
        drwtNo.remove(bnusNo)
        val list = drwtNo.sorted()

        return DrwtNo(null, list[0], list[1], list[2], list[3], list[4], list[5], bnusNo)
    }

    data class State(
        val drwtNo: DrwtNo? = null
    ) : ReactorViewModel.State {

    }
}