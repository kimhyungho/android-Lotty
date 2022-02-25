package com.anseolab.lotty.view.model

import androidx.annotation.LayoutRes
import com.anseolab.lotty.R
import com.anseolab.lotty.view.base.ViewType

enum class DrwtNoViewType(
    @LayoutRes
    override val layoutResId: Int
): ViewType {
    ITEM(R.layout.item_drwt_no);


    override val viewType: Int
        get() = this.ordinal

}