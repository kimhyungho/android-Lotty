package com.anseolab.lotty.view.main.home

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.R
import com.anseolab.lotty.providers.resource.ResourceProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.main.home.mapper.SlotMachineStateMapper
import com.anseolab.lotty.view.model.BannerUiModel
import com.anseolab.lotty.view.model.SlotMachineUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val resourceProvider: ResourceProvider,
    private val slotMachineStateMapper: SlotMachineStateMapper
) : ReactorViewModel<HomeViewModel.Action, HomeViewModel.Mutation, HomeViewModel.State>(
    stateHandle, schedulerProvider
), HomeViewModelType, HomeViewModelType.Input, HomeViewModelType.Output {

    override fun onPageStateChange(lastItem: Int) =
        createAction(Action.PageScrollStateChange(lastItem))

    override fun onPageChange(page: Int) =
        createAction(Action.PageChange(page))

    private val _drawables: MutableLiveData<List<SlotMachineUiModel>> = MutableLiveData()
    override val drawables: LiveData<List<SlotMachineUiModel>> get() = _drawables

    private val _currentItem: MutableLiveData<Int> = MutableLiveData()
    override val currentItem: LiveData<Int> get() = _currentItem

    private val _lastItem: MutableLiveData<Int> = MutableLiveData()
    override val lastItem: LiveData<Int> get() = _lastItem

    private val _banners: MutableLiveData<List<BannerUiModel>> = MutableLiveData()
    override val banners: LiveData<List<BannerUiModel>> get() = _banners

    private val _page: MutableLiveData<Int> = MutableLiveData()
    override val page: LiveData<Int> get() = _page

    override val input: HomeViewModelType.Input = this
    override val output: HomeViewModelType.Output = this

    init {
        state.select(State::lastItem)
            .unwrapOptional()
            .bind(_lastItem)

        state.distinctUntilChanged(slotMachineStateMapper::diff)
            .map(slotMachineStateMapper::mapToView)
            .bind(_drawables)

        _banners.value = listOf(
            BannerUiModel(
                resourceProvider.getDrawable(R.drawable.ic_rocket_32x32)!!,
                "https://dhlottery.co.kr/gameInfo.do?method=buyLotto"
            ),
            BannerUiModel(
                resourceProvider.getDrawable(R.drawable.ic_diamond_32x32)!!,
                "https://dhlottery.co.kr/gameInfo.do?method=game720Method"
            ),
            BannerUiModel(
                resourceProvider.getDrawable(R.drawable.ic_clover_32x32)!!,
                "https://dhlottery.co.kr/gameInfo.do?method=inetbokBuyInfo"
            )
        )

        state.select(State::page)
            .bind(_page)
    }


    override fun mutate(action: Action): Observable<out Mutation> {
        return when (action) {
            is Action.PageScrollStateChange -> {
                Observable.just(Mutation.SetLastItem(action.lastItem))
            }

            is Action.PageChange -> {
                Observable.just(Mutation.SetPage(action.page))
            }

            else -> Observable.empty()
        }
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is Mutation.SetLastItem -> {
                state.copy(lastItem = mutation.lastItem)
            }

            is Mutation.SetPage -> {
                state.copy(page= mutation.page)
            }

            else -> state
        }
    }

    override fun createInitialState(savedState: Parcelable?): State {
        val defaultState= savedState as? State.SavedState
        return State(page = defaultState?.page ?: 0)
    }

    interface Action : ReactorViewModel.Action {
        class PageScrollStateChange(val lastItem: Int) : Action

        class PageChange(val page: Int): Action
    }

    interface Mutation : ReactorViewModel.Mutation {
        class SetLastItem(val lastItem: Int) : Mutation

        class SetPage(val page: Int): Mutation
    }

    data class State(
        val drawables: List<Pair<Int, Int>> = listOf(
            Pair(0, R.drawable.ic_pig_32x32),
            Pair(1, R.drawable.ic_bag_32x32),
            Pair(2, R.drawable.ic_clover_32x32),
            Pair(3, R.drawable.ic_home_32x32),
            Pair(4, R.drawable.ic_diamond_32x32),
            Pair(5, R.drawable.ic_coin_36x36),
            Pair(6, R.drawable.ic_car_32x32),
            Pair(7, R.drawable.ic_bill_32x32),
            Pair(8, R.drawable.ic_rocket_32x32)
        ),
        val lastItem: Int? = null,
        val page: Int
    ) : ReactorViewModel.State {
        override fun toParcelable(): Parcelable? {
            return SavedState(
                this.page
            )
        }

        @Parcelize
        data class SavedState(
            val page: Int
        ): Parcelable
    }
}