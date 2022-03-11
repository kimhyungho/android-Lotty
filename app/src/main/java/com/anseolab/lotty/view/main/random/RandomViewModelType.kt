package com.anseolab.lotty.view.main.random

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.DrwtNoUiModel

interface RandomViewModelType: ViewModelType<RandomViewModelType.Input, RandomViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onDrawButtonClick()
    }

    interface Output: ViewModelType.Output {
        val aList: LiveData<List<Int>>

        val bList: LiveData<List<Int>>

        val cList: LiveData<List<Int>>

        val dList: LiveData<List<Int>>

        val eList: LiveData<List<Int>>

        val publishedDate: LiveData<String>

        val drawDate: LiveData<String>

        val dueDate: LiveData<String>

        val round: LiveData<Long>
    }
}