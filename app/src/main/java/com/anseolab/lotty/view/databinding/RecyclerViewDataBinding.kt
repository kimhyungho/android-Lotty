package com.anseolab.lotty.view.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.base.UiModel

@BindingAdapter("list")
fun <E : UiModel> RecyclerView.bindList(
    items: List<E>?
) {
    this.bindedAdapter?.submitList(items)
}

val RecyclerView.bindedAdapter: BaseRecyclerViewAdapter<*>?
    get() = this.adapter as? BaseRecyclerViewAdapter<*>