package com.anseolab.lotty.view.main.search

import android.util.Log
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentSearchBinding
import com.anseolab.lotty.view.adapter.LotteryListAdapter
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import kotlin.reflect.KClass

@AndroidEntryPoint
class SearchFragment : ViewModelFragment<FragmentSearchBinding, SearchViewModelType>(
    R.layout.fragment_search
) {
    private val _viewModel: SearchViewModel by viewModels()
    override val viewModel: SearchViewModelType get() = _viewModel

    private val lotteryListAdapter = LotteryListAdapter().apply {
        listener = object : LotteryListAdapter.Listener {
            override fun onDrwNoClick(drwNo: Long) {
                viewModel.input.onDrwNoClick(drwNo)
            }
        }
    }

    override fun onWillAttachViewModel(
        viewDataBinding: FragmentSearchBinding,
        viewModel: SearchViewModelType
    ) {
        super.onWillAttachViewModel(viewDataBinding, viewModel)

        with(viewDataBinding) {
            with(rvLottery) {
                this.adapter = lotteryListAdapter
            }

        }
    }

    companion object : FragmentLauncher<SearchFragment>() {
        override val fragmentClass: KClass<SearchFragment>
            get() = SearchFragment::class
    }
}