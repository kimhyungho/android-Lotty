package com.anseolab.lotty.view.main.around

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentAroundBinding
import com.anseolab.lotty.extensions.throttle
import com.anseolab.lotty.view.alert.searchaddress.SearchAddressDialogFragment
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.MainFragmentDirections
import com.jakewharton.rxbinding4.view.clicks
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import kotlin.properties.Delegates
import kotlin.reflect.KClass

@AndroidEntryPoint
class AroundFragment : ViewModelFragment<FragmentAroundBinding, AroundViewModelType>(
    R.layout.fragment_around
) {
    private val _viewModel: AroundViewModel by viewModels()
    override val viewModel: AroundViewModelType get() = _viewModel

    private var oldLong: Double? = null
    private var oldLat: Double? = null

    private var selectedMarker: Marker? = null
    private val currentMarkers: MutableList<Marker> = mutableListOf()

    private lateinit var mNaverMap: NaverMap
    private val mLocationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)

    private val slideUpAnim by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_from_bottom)
    }
    private val slideDownAnim by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_to_bottom).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    viewDataBinding.layoutStoreInfo.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    override fun onWillAttachViewModel(
        viewDataBinding: FragmentAroundBinding,
        viewModel: AroundViewModelType
    ) {
        super.onWillAttachViewModel(viewDataBinding, viewModel)

        (childFragmentManager.findFragmentById(R.id.map) as? MapFragment)?.run {
            getMapAsync { naverMap ->
                mNaverMap = naverMap
                mNaverMap.locationSource = mLocationSource
                mNaverMap.locationTrackingMode = LocationTrackingMode.Follow
                mNaverMap.uiSettings.isRotateGesturesEnabled = false
                mNaverMap.uiSettings.isTiltGesturesEnabled = false
                mNaverMap.setOnMapClickListener { _, _ ->
                    selectedMarker?.iconTintColor = 0
                    selectedMarker = null
                    viewModel.input.onMapClick()
                }
                mNaverMap.addOnCameraIdleListener {
                    if (oldLong == null) oldLong = mNaverMap.cameraPosition.target.longitude
                    if (oldLat == null) oldLat = mNaverMap.cameraPosition.target.longitude

                    if (abs(oldLong!!.minus(mNaverMap.cameraPosition.target.longitude)) > 0.001 ||
                        abs(oldLat!!.minus(mNaverMap.cameraPosition.target.latitude)) > 0.001
                    ) {
                        viewModel.input.onCameraIdleChange(
                            mNaverMap.cameraPosition.target.longitude,
                            mNaverMap.cameraPosition.target.latitude
                        )
                        oldLong = mNaverMap.cameraPosition.target.longitude
                        oldLat = mNaverMap.cameraPosition.target.latitude
                    }
                }
            }
        }

        with(viewDataBinding) {
            btnMyLocation.clicks()
                .bind {
                    val myLocation = mNaverMap.locationOverlay.position
                    mNaverMap.cameraPosition = CameraPosition(myLocation, 13.0)
                }

            tvSearch.clicks()
                .throttle()
                .bind {
                    SearchAddressDialogFragment.apply {
                        listener = object : SearchAddressDialogFragment.Companion.Listener {
                            override fun onSearchButtonClick(address: String) {
                                viewModel.input.onSearchButtonClick(address)
                            }
                        }
                    }.getInstance().show(childFragmentManager, SearchAddressDialogFragment.name)
                }

            btnNavigation.clicks()
                .throttle()
                .bind {
                    val startPoint = mNaverMap.locationOverlay.position
                    val destPoint = _viewModel.currentState.store

                    if (destPoint?.x == null) {
                        Toast.makeText(requireContext(), "목적지를 찾을 수 없습니다.", Toast.LENGTH_SHORT)
                            .show()
                        return@bind
                    }

                    if (checkInstalledKakaoMap()) {
                        val url =
                            Uri.parse("kakaomap://route?sp=${startPoint.latitude},${startPoint.longitude}&ep=${destPoint.y},${destPoint.x}&by=CAR")
                        startActivity(Intent(Intent.ACTION_VIEW, url))
                    } else {
                        openKakaoMapPlayStore()
                    }
                }
        }

        with(viewModel.output) {
            showError.observe {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            showStoreInfo.observe {
                if (it) showStoreInformation()
                else hideStoreInformation()
            }

            stores.observe { stores ->
                val fistStore = stores.firstOrNull()

                if (fistStore != null && _viewModel.currentState.showStoreLocation) mNaverMap.cameraPosition =
                    CameraPosition(LatLng(fistStore.y, fistStore.x), 13.0)

                for (marker in currentMarkers) {
                    if (marker != selectedMarker)
                        marker.map = null
                }
                currentMarkers
                    .removeIf { marker -> marker != selectedMarker }

                for (store in stores) {
                    val storeLocation = LatLng(store.y, store.x)
                    if (selectedMarker?.position?.latitude != store.y && selectedMarker?.position?.longitude != store.x) {
                        val marker = Marker().apply {
                            position = storeLocation
                            map = mNaverMap
                            setOnClickListener {
                                viewModel.input.onMarkerClick(store)
                                selectedMarker?.iconTintColor = 0
                                selectedMarker = this
                                this.iconTintColor = Color.RED
                                true
                            }
                        }
                        currentMarkers.add(marker)
                    }
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun hideStoreInformation() {
        viewDataBinding.layoutStoreInfo.startAnimation(slideDownAnim)
    }

    private fun showStoreInformation() {
        viewDataBinding.layoutStoreInfo.visibility = View.VISIBLE
        viewDataBinding.layoutStoreInfo.startAnimation(slideUpAnim)
    }

    private fun checkInstalledKakaoMap(): Boolean {
        val pm = requireContext().packageManager

        return try {
            pm.getPackageInfo(KAKAO_MAP_PACKAGE_NAME, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openKakaoMapPlayStore() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(KAKAO_MAP_DOWNLOAD_PAGE)))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (mLocationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!mLocationSource.isActivated) {
                return
            }
            mNaverMap.locationTrackingMode = LocationTrackingMode.Follow
        }
    }

    override fun onDestroyView() {
        mNaverMap.removeOnCameraChangeListener { _, _ -> }
        super.onDestroyView()
    }

    companion object : FragmentLauncher<AroundFragment>() {
        const val KAKAO_MAP_PACKAGE_NAME = "net.daum.android.map"
        const val KAKAO_MAP_DOWNLOAD_PAGE =
            "https://play.google.com/store/apps/details?id=net.daum.android.map"
        const val PERMISSION_REQUEST_CODE = 99

        override val fragmentClass: KClass<AroundFragment>
            get() = AroundFragment::class
    }
}