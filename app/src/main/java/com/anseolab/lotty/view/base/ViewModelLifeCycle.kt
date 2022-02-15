package com.anseolab.lotty.view.base

import androidx.databinding.ViewDataBinding

interface ViewModelLifeCycle<VD: ViewDataBinding, VM: ViewModelType<*, *>> {
    fun onWillAttachViewModel(
        viewDataBinding: VD,
        viewModel: VM
    ) {

    }

    fun onDidAttachViewModel(
        viewDataBinding: VD,
        viewModel: VM
    ) {

    }

    fun onWillDetachViewModel(
        viewDataBinding: VD,
        viewModel: VM
    ) {

    }

    fun onDidDetachViewModel() {}

}