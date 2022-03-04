package com.anseolab.lotty.view.main.home

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.R
import com.anseolab.lotty.providers.resource.ResourceProvider
import com.anseolab.lotty.view.base.ReactorViewModel
import com.anseolab.lotty.view.model.SlotMachineUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,

    private val resourceProvider: ResourceProvider
) : ReactorViewModel<HomeViewModel.Action, HomeViewModel.Mutation, HomeViewModel.State>(
    stateHandle, schedulerProvider
), HomeViewModelType, HomeViewModelType.Input, HomeViewModelType.Output {

    private val _drawables: MutableLiveData<List<SlotMachineUiModel>> = MutableLiveData()
    override val drawables: LiveData<List<SlotMachineUiModel>> get() = _drawables

    private val _currentItem: MutableLiveData<Int> = MutableLiveData()
    override val currentItem: LiveData<Int> get() = _currentItem

    override val input: HomeViewModelType.Input = this
    override val output: HomeViewModelType.Output = this

    init {
        _drawables.value = listOf(
            SlotMachineUiModel(
                resourceProvider.getDrawable(R.drawable.ic_clear_12x12)!!
            ),
            SlotMachineUiModel(
                resourceProvider.getDrawable(R.drawable.ic_back_24x24)!!,
            ),
            SlotMachineUiModel(
                resourceProvider.getDrawable(R.drawable.ic_arrow_down_24x24)!!
            ),
            SlotMachineUiModel(
                resourceProvider.getDrawable(R.drawable.ic_clear_12x12)!!
            ),
            SlotMachineUiModel(
                resourceProvider.getDrawable(R.drawable.ic_back_24x24)!!,
            )
        )
    }

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    interface Action : ReactorViewModel.Action {

    }

    interface Mutation : ReactorViewModel.Mutation {

    }

    data class State(
        val name: String = ""
    ) : ReactorViewModel.State {

    }
}