package com.anseolab.lotty.view.alert.searchaddress

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentSearchAddressDialogBinding
import com.anseolab.lotty.view.adapter.RecentAddressListAdapter
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelDialogFragment
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SearchAddressDialogFragment:  ViewModelDialogFragment<FragmentSearchAddressDialogBinding, SearchAddressViewModelType>(
    R.layout.fragment_search_address_dialog
){
    private val _viewModel: SearchAddressViewModel by viewModels()
    override val viewModel: SearchAddressViewModelType get() = _viewModel

    private val recentAddressListAdapter = RecentAddressListAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.attributes?.windowAnimations = R.style.SlideAnimation
    }

    override fun onWillAttachViewModel(
        viewDataBinding: FragmentSearchAddressDialogBinding,
        viewModel: SearchAddressViewModelType
    ) {
        super.onWillAttachViewModel(viewDataBinding, viewModel)

        with(viewDataBinding) {
            rvRecent.adapter = recentAddressListAdapter

            ibBack.clicks()
                .bind {
                    dismiss()
                }

            btnSearch.clicks()
                .bind {
                    val address = _viewModel.currentState.address
                    listener?.onSearchButtonClick(address)
                    dismiss()
                }
        }
    }

    override fun onDestroyView() {
        listener = null
        super.onDestroyView()
    }

    companion object: FragmentLauncher<SearchAddressDialogFragment>() {
        override val fragmentClass: KClass<SearchAddressDialogFragment>
            get() = SearchAddressDialogFragment::class

        var listener: Listener? = null

        interface Listener {
            fun onSearchButtonClick(address: String)
        }
    }

}