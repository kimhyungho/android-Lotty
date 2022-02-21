package com.anseolab.lotty.view.main.around

import androidx.lifecycle.LiveData
import com.anseolab.domain.model.Store
import com.anseolab.lotty.view.base.ViewModelType

interface AroundViewModelType: ViewModelType<AroundViewModelType.Input, AroundViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onCameraIdleChange(x: Double, y: Double)
    }

    interface Output: ViewModelType.Output {
        val stores: LiveData<List<Store>>
    }
}