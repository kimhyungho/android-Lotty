package com.anseolab.lotty.view.alert.searchaddress

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentSearchAddressDialogBinding
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SearchAddressDialogFragment:  ViewModelDialogFragment<FragmentSearchAddressDialogBinding, SearchAddressViewModelType>(
    R.layout.fragment_search_address_dialog
){
    private val _viewModel: SearchAddressViewModel by viewModels()
    override val viewModel: SearchAddressViewModelType get() = _viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.attributes?.windowAnimations = R.style.SlideAnimation
    }

    override fun onStart() {
        super.onStart()

//        dialog?.window?.setWindowAnimations(R.style.SlideAnimation)
    }

    companion object: FragmentLauncher<SearchAddressDialogFragment>() {
        override val fragmentClass: KClass<SearchAddressDialogFragment>
            get() = SearchAddressDialogFragment::class
    }

}