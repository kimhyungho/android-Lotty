package com.anseolab.lotty.view.main.random

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.DrwtNoUiModel

interface RandomViewModelType: ViewModelType<RandomViewModelType.Input, RandomViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onCreateButtonClick()
    }

    interface Output: ViewModelType.Output {
        val drwtNo: LiveData<DrwtNoUiModel>
    }
}