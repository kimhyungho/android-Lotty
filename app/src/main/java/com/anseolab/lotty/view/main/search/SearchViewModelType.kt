package com.anseolab.lotty.view.main.search

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.LotteryUiModel

interface SearchViewModelType: ViewModelType<SearchViewModelType.Input, SearchViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun refresh()

        fun onScroll(drwNo: Long)

        fun onDrwNoClick(drwNo: Long)
    }

    interface Output: ViewModelType.Output {
        val lotteries: LiveData<List<LotteryUiModel>>

        val isRefreshing: LiveData<Boolean>

        val isLoading: LiveData<Boolean>
    }
}