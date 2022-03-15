package com.anseolab.lotty.view.main.qr

import android.Manifest
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentQrBinding
import com.anseolab.lotty.view.base.BaseFragment
import com.anseolab.lotty.view.base.FragmentLauncher
import com.gun0912.tedpermission.rx3.TedPermission
import github.nisrulz.qreader.QRDataListener
import github.nisrulz.qreader.QREader
import kotlin.reflect.KClass

class QrFragment : BaseFragment<FragmentQrBinding>(
    R.layout.fragment_qr
) {

    private var qReader: QREader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TedPermission.create()
            .setPermissions(Manifest.permission.CAMERA)
            .request()
            .subscribe { result ->
                if (result.isGranted) {

                } else {
                    Toast.makeText(
                        requireContext(),
                        "카메라 권한을 허가해주셔야 사용하실 수 있습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            TedPermission.create()
                .setPermissions(Manifest.permission.CAMERA)
                .request()
                .subscribe { result ->
                    if (result.isGranted) {
                        qReader = object : QREader.Builder(requireContext(),viewDataBinding.surfaceView, object : QRDataListener {
                            override fun onDetected(data: String?) {

                            }
                        }).facing(QREader.BACK_CAM)
                            .enableAutofocus(true)
                            .height(viewDataBinding.surfaceView.height)
                            .width(viewDataBinding.surfaceView.width)
                            .build()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "카메라 권한을 허가해주셔야 사용하실 수 있습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(viewDataBinding) {
            with(webView) {
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = false
                settings.javaScriptCanOpenWindowsAutomatically = true
            }

        }
    }


    companion object : FragmentLauncher<QrFragment>() {
        override val fragmentClass: KClass<QrFragment>
            get() = QrFragment::class
    }
}