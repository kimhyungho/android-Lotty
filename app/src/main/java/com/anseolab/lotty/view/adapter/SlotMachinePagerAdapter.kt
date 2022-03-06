package com.anseolab.lotty.view.adapter

import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.base.Differable
import com.anseolab.lotty.view.model.SlotMachineUiModel
import com.anseolab.lotty.view.model.SlotMachineViewType

class SlotMachinePagerAdapter : BaseRecyclerViewAdapter<SlotMachineUiModel>(
    viewTypes = SlotMachineViewType.values()
) {

    override fun getItemCount(): Int {
        return if (super.getItemCount() == 0) 0 else Int.MAX_VALUE
    }

    override fun getItem(position: Int): Differable? {
        return if (itemCount == 0) null else super.getItem(position % 9)
    }
}