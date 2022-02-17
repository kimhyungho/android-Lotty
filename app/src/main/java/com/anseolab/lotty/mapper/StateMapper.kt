package com.anseolab.lotty.mapper

import com.anseolab.lotty.view.base.ReactorViewModel

internal interface StateMapper<State : ReactorViewModel.State, T> {
    fun mapToView(state: State): T

    fun diff(old: State, new: State): Boolean = false
}