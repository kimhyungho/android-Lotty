package com.anseolab.lotty.view.main.around

import androidx.lifecycle.LiveData
import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.model.Store
import com.anseolab.lotty.view.base.ViewModelType

interface AroundViewModelType: ViewModelType<AroundViewModelType.Input, AroundViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onCameraIdleChange(x: Double, y: Double)

        fun onMarkerClick(store: KakaoStore)

        fun onMapClick()
    }

    interface Output: ViewModelType.Output {
        val store: LiveData<KakaoStore>

        val stores: LiveData<List<KakaoStore>>

        val showStoreInfo: LiveData<Boolean>
    }
}