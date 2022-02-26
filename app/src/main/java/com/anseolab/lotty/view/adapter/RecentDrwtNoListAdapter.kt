package com.anseolab.lotty.view.adapter

import com.anseolab.domain.model.Lottery
import com.anseolab.lotty.databinding.ItemDrwtNoBinding
import com.anseolab.lotty.databinding.ItemRecentLotteryBinding
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.model.DrwtNoUiModel
import com.anseolab.lotty.view.model.DrwtNoViewType

class RecentDrwtNoListAdapter : BaseRecyclerViewAdapter<DrwtNoUiModel>(
    viewTypes = DrwtNoViewType.values()
) {
    var listener: Listener? = null

    override fun onBindListener(viewHolder: BaseViewHolder<DrwtNoUiModel>) {
        super.onBindListener(viewHolder)
        when (val binding = viewHolder.viewDataBinding) {
            is ItemDrwtNoBinding -> {
                binding.ibDelete.setOnClickListener {
                    val drwNo = getCurrentItem(viewHolder)?.id ?: return@setOnClickListener
                    listener?.onRemoveClick(drwNo)
                }
            }
        }
    }

    interface Listener {
        fun onRemoveClick(id: Int)
    }
}