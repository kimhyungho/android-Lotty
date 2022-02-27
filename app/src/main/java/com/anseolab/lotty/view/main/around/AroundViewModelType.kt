package com.anseolab.lotty.view.main.around

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.model.Store
import com.anseolab.lotty.view.base.ViewModelType
import com.naver.maps.map.overlay.Marker

interface AroundViewModelType: ViewModelType<AroundViewModelType.Input, AroundViewModelType.Output> {
    interface Input: ViewModelType.Input {
        fun onCameraIdleChange(x: Double, y: Double)

        fun onMarkerClick(store: KakaoStore)

        fun onSearchButtonClick(address: String)

        fun onMapClick()
    }

    interface Output: ViewModelType.Output {
        val store: LiveData<KakaoStore>

        val stores: LiveData<List<KakaoStore>>

        val showStoreInfo: LiveData<Boolean>

        val showStoreLocation: LiveData<Boolean>
    }
}