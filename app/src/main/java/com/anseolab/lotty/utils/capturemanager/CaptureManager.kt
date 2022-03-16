package com.anseolab.lotty.utils.capturemanager

import android.app.Activity
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class CaptureManager(
    activity: Activity,
    private val barcodeView: DecoratedBarcodeView
) : CaptureManager(activity, barcodeView) {

    private var listener: Listener? = null
    private var currentResult: String? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun closeAndFinish() {
    }

    override fun returnResult(rawResult: BarcodeResult?) {
        val result = rawResult?.text ?: ""
        if (result != currentResult) {
            listener?.onCapture(result)
            setCurrentAddress(result = result)
        }
        barcodeView.resume()
        this.decode()
    }

    private fun setCurrentAddress(result: String) {
        currentResult = result
    }

    interface Listener {
        fun onCapture(result: String)
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }
}