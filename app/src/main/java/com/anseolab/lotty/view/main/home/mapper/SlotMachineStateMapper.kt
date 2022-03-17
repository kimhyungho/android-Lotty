package com.anseolab.lotty.view.main.home.mapper

import com.anseolab.lotty.mapper.StateMapper
import com.anseolab.lotty.providers.resource.ResourceProvider
import com.anseolab.lotty.view.model.SlotMachineUiModel
import javax.inject.Inject

//class SlotMachineStateMapper @Inject constructor(
//    private val resourceProvider: ResourceProvider
//) : StateMapper<HomeViewModel.State, List<SlotMachineUiModel>> {
//    override fun mapToView(state: HomeViewModel.State): List<SlotMachineUiModel> {
//        return state.drawables.map {
//            SlotMachineUiModel(
//                id = it.first,
//                drawable = resourceProvider.getDrawable(it.second)!!,
//                isLastModel = it.first == (state.lastItem ?: 0) % SlotMachineUiModel.ITEM_COUNT
//            )
//        }
//    }
//
//    override fun diff(old: HomeViewModel.State, new: HomeViewModel.State): Boolean {
//        return old.drawables === new.drawables && old.lastItem === new.lastItem
//    }
//}