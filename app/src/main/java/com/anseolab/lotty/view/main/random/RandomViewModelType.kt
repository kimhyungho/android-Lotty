package com.anseolab.lotty.view.main.random

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.DrwtNoUiModel

interface RandomViewModelType: ViewModelType<RandomViewModelType.Input, RandomViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onCreateButtonClick()

        fun onClearButtonClick()

        fun onRemoveButtonClick(id: Int)
    }

    interface Output: ViewModelType.Output {
        val drwtNo: LiveData<DrwtNoUiModel>

        val recentDrwtNos: LiveData<List<DrwtNoUiModel>>
    }
}