package com.anseolab.lotty.view.main.home

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.BannerUiModel
import com.anseolab.lotty.view.model.SlotMachineUiModel

interface HomeViewModelType: ViewModelType<HomeViewModelType.Input, HomeViewModelType.Output> {
    interface Input : ViewModelType.Input{
        fun onPageStateChange(lastItem: Int)

        fun onPageChange(page: Int)
    }

    interface Output: ViewModelType.Output {
        val currentItem: LiveData<Int>

        val drawables : LiveData<List<SlotMachineUiModel>>

        val lastItem: LiveData<Int>

        val banners: LiveData<List<BannerUiModel>>

        val page: LiveData<Int>

    }
}