package com.anseolab.lotty.view.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentDetailBinding
import com.anseolab.lotty.view.base.ViewModelFragment
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: ViewModelFragment<FragmentDetailBinding, DetailViewModelType>(
    R.layout.fragment_detail
) {

    private val _viewModel: DetailViewModel by viewModels()
    override val viewModel: DetailViewModelType get() = _viewModel

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.input.fetchLottery(args.lottery)
    }

    override fun onWillAttachViewModel(
        viewDataBinding: FragmentDetailBinding,
        viewModel: DetailViewModelType
    ) {
        super.onWillAttachViewModel(viewDataBinding, viewModel)

        with(viewDataBinding) {
            ibBack.clicks()
                .bind {
                    onBackPressed()
                }
        }
    }
}