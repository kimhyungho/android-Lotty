package com.anseolab.lotty.view.alert.searchaddress

import androidx.lifecycle.LiveData
import com.anseolab.lotty.view.base.ViewModelType
import com.anseolab.lotty.view.model.RecentAddressUiModel

interface SearchAddressViewModelType :
    ViewModelType<SearchAddressViewModelType.Input, SearchAddressViewModelType.Output> {
    interface Input : ViewModelType.Input {
        fun onAddressTextChange(address: String)
    }

    interface Output : ViewModelType.Output {
        val recentAddresses: LiveData<List<RecentAddressUiModel>>

        val address: LiveData<String>
    }
}