package com.anseolab.lotty.view.adapter

import android.content.res.ColorStateList
import android.widget.TextView
import com.anseolab.domain.model.Lottery
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.ItemLotteryBinding
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.model.LotteryUiModel
import com.anseolab.lotty.view.model.LotteryViewType
import java.time.LocalDate

class LotteryListAdapter : BaseRecyclerViewAdapter<LotteryUiModel>(
    viewTypes = LotteryViewType.values()
) {

    var listener: Listener? = null

    override fun onBindListener(viewHolder: BaseViewHolder<LotteryUiModel>) {
        super.onBindListener(viewHolder)
        when (val binding = viewHolder.viewDataBinding) {
            is ItemLotteryBinding -> {
                binding.tvRound.setOnClickListener {
                    val drwNo = getCurrentItem(viewHolder)?.drwNo ?: return@setOnClickListener
                    listener?.onDrwNoClick(drwNo)
                }

                binding.btnDetail.setOnClickListener {
                    val item = getCurrentItem(viewHolder)
                    listener?.onDetailClick(
                        Lottery(
                            totSellamnt = item?.totSellamnt,
                            returnValue = if(item?.returnValue == true) "success" else "fail",
                            drwNoDate = LocalDate.parse(item?.drwNoDate),
                            firstWinamnt = item?.firstWinamnt,
                            firstAccumamnt = item?.firstAccumamnt,
                            drwtNo1 = item?.drwtNo1,
                            drwtNo2 = item?.drwtNo2,
                            drwtNo3 = item?.drwtNo3,
                            drwtNo4 = item?.drwtNo4,
                            drwtNo5 = item?.drwtNo5,
                            drwtNo6 = item?.drwtNo6,
                            bnusNo = item?.bnusNo,
                            firstPrzwnerCo = item?.firstPrzwnerCo,
                            drwNo = item?.drwNo
                        )
                    )
                }
            }
        }
    }

    fun getItemDrwNum(position: Int): Long {
        return getItem(position).identifier as Long
    }

    interface Listener {
        fun onDrwNoClick(drwNo: Long)

        fun onDetailClick(lottery: Lottery)
    }

}