package com.anseolab.lotty.view.main.around

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType

interface AroundViewModelType: ViewModelType<AroundViewModelType.Input, AroundViewModelType.Output> {
    interface Input: ViewModelType.Input {
    }

    interface Output: ViewModelType.Output {
    }
}