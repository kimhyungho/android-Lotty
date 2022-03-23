package com.anseolab.lotty.utils.webview

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient


class WebViewClient(
    private val context: Context
) : WebViewClient() {
    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("이 사이트의 보안 인증서는 신뢰하는 보안 인증서가 아닙니다. 계속하시겠습니까?")
        builder.setPositiveButton("계속하기") { _, _ -> handler?.proceed() }
        builder.setNegativeButton("취소") { _, _ -> handler?.cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}