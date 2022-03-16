package com.anseolab.lotty.view.adapter

import com.anseolab.lotty.databinding.ItemAddressBinding
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.model.RecentAddressUiModel
import com.anseolab.lotty.view.model.RecentAddressViewType

class RecentAddressListAdapter : BaseRecyclerViewAdapter<RecentAddressUiModel>(
    viewTypes = RecentAddressViewType.values()
) {

    var listener: Listener? = null

    override fun onBindListener(viewHolder: BaseViewHolder<RecentAddressUiModel>) {
        super.onBindListener(viewHolder)
        when (val binding = viewHolder.viewDataBinding) {
            is ItemAddressBinding -> {
                binding.root.setOnClickListener {
                    val address = getCurrentItem(viewHolder)?.address ?: ""
                    listener?.onClickItem(address)
                }
            }
        }
    }

    interface Listener {
        fun onClickItem(address: String)
    }
}