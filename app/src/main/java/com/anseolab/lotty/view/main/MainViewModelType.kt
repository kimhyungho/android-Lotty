package com.anseolab.lotty.view.main

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType

interface MainViewModelType: ViewModelType<MainViewModelType.Input, MainViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onPageSelect(page: Int)
    }

    interface Output: ViewModelType.Output {
        val selectedPage: LiveData<Int>
    }
}