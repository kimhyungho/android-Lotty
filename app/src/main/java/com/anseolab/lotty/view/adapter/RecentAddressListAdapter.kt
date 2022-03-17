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
                binding.tvAddress.setOnClickListener {
                    val address = getCurrentItem(viewHolder)?.address ?: ""
                    listener?.onTextClick(address)
                }

                binding.ibDelete.setOnClickListener {
                    val address = getCurrentItem(viewHolder)?.address ?: ""
                    listener?.onRemoveButtonClick(address)
                }
            }
        }
    }

    interface Listener {
        fun onTextClick(address: String)

        fun onRemoveButtonClick(address: String)
    }
}