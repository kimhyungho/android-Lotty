package com.anseolab.lotty.view.main.random

import com.anseolab.lotty.view.base.ViewModelType

interface RandomViewModelType: ViewModelType<RandomViewModelType.Input, RandomViewModelType.Output> {
    interface Input: ViewModelType.Input {
    }

    interface Output: ViewModelType.Output {
    }
}