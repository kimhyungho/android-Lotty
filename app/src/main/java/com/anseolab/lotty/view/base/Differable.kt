package com.anseolab.lotty.view.base

import androidx.recyclerview.widget.DiffUtil

interface Differable : Identifiable, Equatable {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Differable>() {
            override fun areItemsTheSame(oldItem: Differable, newItem: Differable): Boolean {
                return oldItem.identifier == newItem.identifier
            }

            override fun areContentsTheSame(oldItem: Differable, newItem: Differable): Boolean {
                // add Equatable
                return oldItem == newItem
            }
        }
    }
}