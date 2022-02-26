package com.anseolab.lotty.view.main.random

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.data.mapper.DrwtNoMapper
import com.anseolab.domain.interactors.drwtno.ClearDrwtNoUseCase
import com.anseolab.domain.interactors.drwtno.FetchDrwtNoUseCase
import com.anseolab.domain.interactors.drwtno.RemoveDrwtNoUseCase
import com.anseolab.domain.interactors.drwtno.SetDrwtNoUseCase
import com.anseolab.domain.model.DrwtNo
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.main.around.AroundViewModelType
import com.anseolab.lotty.view.main.random.mapper.DrwtNoStateMapper
import com.anseolab.lotty.view.main.random.mapper.RecentDrwtNosStateMapper
import com.anseolab.lotty.view.model.DrwtNoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val setDrwtNoUseCase: SetDrwtNoUseCase,
    private val fetchDrwtNoUseCase: FetchDrwtNoUseCase,
    private val clearDrwtNoUseCase: ClearDrwtNoUseCase,
    private val removeDrwtNoUseCase: RemoveDrwtNoUseCase
) : ReactorViewModel<RandomViewModel.Action, RandomViewModel.Mutation, RandomViewModel.State>(
    stateHandle, schedulerProvider
), RandomViewModelType, RandomViewModelType.Input, RandomViewModelType.Output {
    override fun onCreateButtonClick() =
        createAction(Action.CreateButtonClick)

    override fun onClearButtonClick() =
        createAction(Action.ClearButtonClick)

    override fun onRemoveButtonClick(id: Int) =
        createAction(Action.RemoveButtonClick(id))

    private val _drwtNo: MutableLiveData<DrwtNoUiModel> = MutableLiveData()
    override val drwtNo: LiveData<DrwtNoUiModel> get() = _drwtNo

    private val _recentDrwtNos: MutableLiveData<List<DrwtNoUiModel>> = MutableLiveData()
    override val recentDrwtNos: LiveData<List<DrwtNoUiModel>> get() = _recentDrwtNos

    override val input: RandomViewModelType.Input = this
    override val output: RandomViewModelType.Output = this

    init {
        state.distinctUntilChanged(DrwtNoStateMapper::diff)
            .map(DrwtNoStateMapper::mapToView)
            .bind(_drwtNo)

        state.distinctUntilChanged(RecentDrwtNosStateMapper::diff)
            .map(RecentDrwtNosStateMapper::mapToView)
            .bind(_recentDrwtNos)
    }

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    override fun transformAction(action: Observable<Action>): Observable<out Action> {
        return action.startWithItem(Action.FetchRecentDrwtNo)
    }

    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.FetchRecentDrwtNo -> {
                fetchDrwtNoUseCase.execute()
                    .map(Mutation::SetRecentDrwtNos)
                    .toObservable()
            }

            is Action.ClearButtonClick -> {
                clearDrwtNoUseCase.execute()
                    .toObservable()
            }

            is Action.RemoveButtonClick -> {
                val id = action.id
                removeDrwtNoUseCase.execute(id)
                    .toObservable()
            }

            is Action.CreateButtonClick -> {
                val drwtNo = createDrwtNo()
                val params = SetDrwtNoUseCase.Params(
                    drwtNo1 = drwtNo.drwtNo1,
                    drwtNo2 = drwtNo.drwtNo2,
                    drwtNo3 = drwtNo.drwtNo3,
                    drwtNo4 = drwtNo.drwtNo4,
                    drwtNo5 = drwtNo.drwtNo5,
                    drwtNo6 = drwtNo.drwtNo6,
                    bnusNo = drwtNo.bnusNo
                )
                setDrwtNoUseCase.execute(params)
                    .andThen(Observable.just(Mutation.SetDrwtNo(drwtNo)))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.SetDrwtNo -> {
                state.copy(drwtNo = mutation.drwtNo)
            }

            is Mutation.SetRecentDrwtNos -> {
                state.copy(recentDrwtNo = mutation.drwtNos)
            }

            else -> state
        }
    }

    interface Action : ReactorViewModel.Action {
        object FetchRecentDrwtNo : Action

        object CreateButtonClick : Action

        object ClearButtonClick: Action

        class RemoveButtonClick(val id: Int): Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetDrwtNo(val drwtNo: DrwtNo) : Mutation

        class SetRecentDrwtNos(val drwtNos: List<DrwtNo>) : Mutation
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
        val drwtNo: DrwtNo? = null,
        val recentDrwtNo: List<DrwtNo> = listOf()
    ) : ReactorViewModel.State {

    }
}