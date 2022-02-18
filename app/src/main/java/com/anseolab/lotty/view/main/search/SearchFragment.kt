package com.anseolab.lotty.view.main.search

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentSearchBinding
import com.anseolab.lotty.providers.permissions.PermissionProvider
import com.anseolab.lotty.view.adapter.LotteryListAdapter
import com.anseolab.lotty.view.alert.scanner.ScannerAlertDialog
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.MainFragmentDirections
import com.gun0912.tedpermission.rx3.TedPermission
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
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

    private var rvLotteryScrollListener: RecyclerView.OnScrollListener? =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                val totalItemViewCount = recyclerView.adapter?.itemCount?.minus(1) ?: 0

                Log.d("kkkk", totalItemViewCount.toString())

                if (newState == 2 && !recyclerView.canScrollVertically(1) && lastVisibleItem == totalItemViewCount) {
                    val lastItemDrwNum = (recyclerView.adapter as LotteryListAdapter).getItemDrwNum(
                        totalItemViewCount
                    )
                    Log.d("kkkkdd", totalItemViewCount.toString())

                    viewModel.input.onScroll(lastItemDrwNum - 1)
                }

            }
        }


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
                if (rvLotteryScrollListener != null) {
                    viewDataBinding.rvLottery.addOnScrollListener(rvLotteryScrollListener!!)
                }
                this.adapter = lotteryListAdapter
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_search -> {
                        navController.navigate(MainFragmentDirections.actionDestMainToDestDetail())
                        true
                    }

                    R.id.menu_qr -> {
                        TedPermission.create()
                            .setPermissions(Manifest.permission.CAMERA)
                            .request()
                            .subscribe { result ->
                                if (result.isGranted) {
                                    ScannerAlertDialog.getInstance()
                                        .show(childFragmentManager, ScannerAlertDialog.name)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "카메라 권한을 허가해주셔야 사용하실 수 있습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        true
                    }
                    else -> false
                }
            }

            srlLottery.refreshes()
                .bind {
                    viewModel.input.refresh()
                }


        }

        with(viewModel.output) {

        }
    }

    override fun onDestroyView() {
        viewDataBinding.rvLottery.adapter = null
        if (rvLotteryScrollListener != null) {
            viewDataBinding.rvLottery.removeOnScrollListener(rvLotteryScrollListener!!)
        }
            super.onDestroyView()
    }

    companion object : FragmentLauncher<SearchFragment>() {
        override val fragmentClass: KClass<SearchFragment>
            get() = SearchFragment::class
    }
}