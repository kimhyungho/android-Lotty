package com.anseolab.lotty.view.adapter

import com.anseolab.lotty.databinding.ItemLotteryBinding
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.model.LotteryUiModel
import com.anseolab.lotty.view.model.LotteryViewType

class LotteryListAdapter : BaseRecyclerViewAdapter<LotteryUiModel>(
    viewTypes = LotteryViewType.values()
) {

    var listener: Listener? = null

    override fun onBindListener(viewHolder: BaseViewHolder<LotteryUiModel>) {
        super.onBindListener(viewHolder)
        when(val binding = viewHolder.viewDataBinding) {
            is ItemLotteryBinding -> {
                binding.tvRound.setOnClickListener {
                    val drwNo = getCurrentItem(viewHolder)?.drwNo ?: return@setOnClickListener
                    listener?.onDrwNoClick(drwNo)
                }
            }
        }
    }

    interface Listener {
        fun onDrwNoClick(drwNo: Long)
    }
}