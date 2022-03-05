package com.anseolab.lotty.view.adapter

import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.base.Differable
import com.anseolab.lotty.view.model.SlotMachineUiModel
import com.anseolab.lotty.view.model.SlotMachineViewType

class SlotMachinePagerAdapter: BaseRecyclerViewAdapter<SlotMachineUiModel>(
    viewTypes = SlotMachineViewType.values()
) {

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun getItem(position: Int): Differable {
        return super.getItem(position % 3)
    }
}