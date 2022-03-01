package com.anseolab.lotty.view.alert.searchaddress.mapper

import com.anseolab.lotty.mapper.StateMapper
import com.anseolab.lotty.view.alert.searchaddress.SearchAddressViewModel
import com.anseolab.lotty.view.model.RecentAddressUiModel

object RecentAddressStateMapper: StateMapper<SearchAddressViewModel.State, List<RecentAddressUiModel>> {
    override fun mapToView(state: SearchAddressViewModel.State): List<RecentAddressUiModel> {
        return state.recentAddresses.map {
            RecentAddressUiModel(
                address = it
            )
        }
    }

    override fun diff(
        old: SearchAddressViewModel.State,
        new: SearchAddressViewModel.State
    ): Boolean {
        return old.recentAddresses === new.recentAddresses
    }
}