package com.anseolab.lotty.view.main.qr

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.widget.Toast
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentQrBinding
import com.anseolab.lotty.utils.capturemanager.CaptureManager
import com.anseolab.lotty.utils.webview.WebViewClient
import com.anseolab.lotty.view.base.BaseFragment
import com.anseolab.lotty.view.base.FragmentLauncher
import com.gun0912.tedpermission.rx3.TedPermission
import kotlin.reflect.KClass

class QrFragment : BaseFragment<FragmentQrBinding>(
    R.layout.fragment_qr
) {

    private var capture: CaptureManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showCameraPermission()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            with(webView) {

                settings.javaScriptCanOpenWindowsAutomatically = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.javaScriptEnabled = true
            }

            with(dbv) {
                capture = CaptureManager(requireActivity(), this).apply {
                    setListener(object : CaptureManager.Listener {
                        override fun onCapture(result: String) {
                            if (result.startsWith("http://m.dhlottery.co.kr/") || result.startsWith("http://qr.645lotto.net/")
                            ) {
                                webView.post { webView.loadUrl(result) }
                                layoutCaution.visibility = View.INVISIBLE
                            } else {
                                Toast.makeText(requireContext(), "복권 QR이 아닙니다.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
                }
                capture?.decode()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        capture?.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture?.onPause()
    }

    override fun onDestroyView() {
        capture?.onDestroy()
        super.onDestroyView()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            showCameraPermission()
        }
    }


    private fun showCameraPermission() {
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

    companion object : FragmentLauncher<QrFragment>() {
        override val fragmentClass: KClass<QrFragment>
            get() = QrFragment::class
    }
}