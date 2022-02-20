package com.anseolab.lotty.view.adapter

import com.anseolab.domain.model.Lottery
import com.anseolab.lotty.databinding.ItemLotteryBinding
import com.anseolab.lotty.databinding.ItemRecentLotteryBinding
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.model.LotteryUiModel
import com.anseolab.lotty.view.model.LotteryViewType
import com.anseolab.lotty.view.model.RecentLotteryUiModel
import com.anseolab.lotty.view.model.RecentLotteryViewType

class RecentLotteryListAdapter : BaseRecyclerViewAdapter<RecentLotteryUiModel>(
    viewTypes = RecentLotteryViewType.values()
) {

    var listener: Listener? = null

    override fun onBindListener(viewHolder: BaseViewHolder<RecentLotteryUiModel>) {
        super.onBindListener(viewHolder)
        when (val binding = viewHolder.viewDataBinding) {
            is ItemRecentLotteryBinding -> {

                binding.ibDelete.setOnClickListener {
                    val drwNo = getCurrentItem(viewHolder)?.drwNo ?: return@setOnClickListener
                    listener?.onDeleteButtonClick(drwNo)
                }

                binding.tvDrwNo.setOnClickListener {
                    val drwNo = getCurrentItem(viewHolder)?.drwNo ?: return@setOnClickListener
                    listener?.onDrwNoClick(drwNo)
                }
            }
        }
    }

    interface Listener {
        fun onDeleteButtonClick(drwNo: Long)

        fun onDrwNoClick(drwNo: Long)
    }

}