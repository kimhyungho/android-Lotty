package com.anseolab.lotty.view.main.around

import android.Manifest
import android.util.Log
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentAroundBinding
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
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

    private val currentMarkers: MutableList<Marker> = mutableListOf()

    private lateinit var mNaverMap: NaverMap
    private val mLocationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)

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

                mNaverMap.addOnCameraIdleListener {
                    if (oldLong == null) oldLong = mNaverMap.cameraPosition.target.longitude
                    if (oldLat == null) oldLat = mNaverMap.cameraPosition.target.longitude

                    if (abs(oldLong!!.minus(mNaverMap.cameraPosition.target.longitude)) > 0.000001 ||
                        abs(oldLat!!.minus(mNaverMap.cameraPosition.target.latitude)) > 0.000001
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

        }

        with(viewModel.output) {


            stores.observe { stores ->
                for(marker in currentMarkers) {
                    marker.map = null
                }
                currentMarkers.clear()
                for(store in stores) {
                    val storeLocation = LatLng(store.y, store.x)
                    val marker = Marker().apply {
                        position = storeLocation
                        map = mNaverMap
                    }
                    currentMarkers.add(marker)
                }
            }
        }
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
        const val PERMISSION_REQUEST_CODE = 99

        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        override val fragmentClass: KClass<AroundFragment>
            get() = AroundFragment::class
    }
}