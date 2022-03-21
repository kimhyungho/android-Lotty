package com.anseolab.lotty.view.main.search

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anseolab.domain.model.Lottery
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentSearchBinding
import com.anseolab.lotty.extensions.throttle
import com.anseolab.lotty.providers.permissions.PermissionProvider
import com.anseolab.lotty.view.adapter.LotteryListAdapter
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.MainFragmentDirections
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class SearchFragment : ViewModelFragment<FragmentSearchBinding, SearchViewModelType>(
    R.layout.fragment_search
) {
    private val _viewModel: SearchViewModel by viewModels()
    override val viewModel: SearchViewModelType get() = _viewModel

    @Inject
    lateinit var permissionProvider: PermissionProvider

    private val rvLotteryScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                val totalItemViewCount = recyclerView.adapter?.itemCount?.minus(1) ?: 0

                if (newState == 2 && !recyclerView.canScrollVertically(1) &&
                    lastVisibleItem == totalItemViewCount &&
                    recyclerView.adapter!!.itemCount > 0
                ) {
                    val lastItemDrwNum = (recyclerView.adapter as LotteryListAdapter).getItemDrwNum(
                        totalItemViewCount
                    )

                    viewModel.input.onScroll(lastItemDrwNum - 1)
                }
            }
        }

    private val lotteryListAdapter = LotteryListAdapter().apply {
        listener = object : LotteryListAdapter.Listener {
            override fun onDrwNoClick(drwNo: Long) {
                viewModel.input.onDrwNoClick(drwNo)
            }

            override fun onDetailClick(lottery: Lottery) {
                navController.navigate(MainFragmentDirections.actionDestMainToDestDetail(lottery))
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
                viewDataBinding.rvLottery.addOnScrollListener(rvLotteryScrollListener)
                this.adapter = lotteryListAdapter
            }

            ibDetail.clicks()
                .bind {
                    navController.navigate(MainFragmentDirections.actionDestMainToDestDetail())
                }

            srlLottery.refreshes()
                .throttle()
                .bind {
                    viewModel.input.refresh()
                }
        }

        with(viewModel.output) {
            showError.observe {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        viewDataBinding.rvLottery.adapter = null
        viewDataBinding.rvLottery.removeOnScrollListener(rvLotteryScrollListener)
        super.onDestroyView()
    }

    companion object : FragmentLauncher<SearchFragment>() {
        override val fragmentClass: KClass<SearchFragment>
            get() = SearchFragment::class
    }
}