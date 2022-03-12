package com.anseolab.lotty.view.adapter

import com.anseolab.lotty.databinding.ItemBannerBinding
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.base.Differable
import com.anseolab.lotty.view.model.BannerUiModel
import com.anseolab.lotty.view.model.BannerViewType

class BannerPagerAdapter : BaseRecyclerViewAdapter<BannerUiModel>(
    viewTypes = BannerViewType.values()
) {

    var mListener: Listener? = null

    override fun onBindListener(viewHolder: BaseViewHolder<BannerUiModel>) {
        super.onBindListener(viewHolder)
        when(val binding = viewHolder.viewDataBinding) {
            is ItemBannerBinding -> {
                binding.root.setOnClickListener {
                    val item = getCurrentItem(viewHolder) ?: return@setOnClickListener
                    mListener?.onItemClick(item.url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() == 0) 0 else Int.MAX_VALUE
    }

    override fun getItem(position: Int): Differable? {
        return if (itemCount == 0) null else super.getItem(position % 3)
    }

    interface Listener {
        fun onItemClick(url: String)
    }
}