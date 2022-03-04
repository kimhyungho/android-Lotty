package com.anseolab.lotty.view.adapter

import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.model.SlotMachineUiModel
import com.anseolab.lotty.view.model.SlotMachineViewType

class SlotMachinePagerAdapter: BaseRecyclerViewAdapter<SlotMachineUiModel>(
    viewTypes = SlotMachineViewType.values()
) {

}