package com.anseolab.lotty.view.adapter

import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.model.RecentAddressUiModel
import com.anseolab.lotty.view.model.RecentAddressViewType

class RecentAddressListAdapter: BaseRecyclerViewAdapter<RecentAddressUiModel>(
    viewTypes = RecentAddressViewType.values()
) {

}