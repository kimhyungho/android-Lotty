package com.anseolab.lotty.view.detail

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType

interface DetailViewModelType: ViewModelType<DetailViewModelType.Input, DetailViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onWordTextChange(word: String)
    }

    interface Output: ViewModelType.Output {
        val word: LiveData<String>
    }
}