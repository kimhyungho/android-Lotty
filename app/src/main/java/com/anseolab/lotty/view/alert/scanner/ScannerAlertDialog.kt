package com.anseolab.lotty.view.alert.scanner

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentScannerDialogBinding
import com.anseolab.lotty.providers.permissions.PermissionProvider
import com.anseolab.lotty.view.base.BaseDialogFragment
import com.anseolab.lotty.view.base.FragmentLauncher
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class ScannerAlertDialog : BaseDialogFragment<FragmentScannerDialogBinding>(
    R.layout.fragment_scanner_dialog
) {
    private lateinit var codeScanner: CodeScanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            with(scannerView) {
                codeScanner = CodeScanner(requireContext(), this)
                codeScanner.decodeCallback = DecodeCallback {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.text)))
                }

                scannerView.setOnClickListener {
                    codeScanner.startPreview()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    companion object : FragmentLauncher<ScannerAlertDialog>() {
        override val fragmentClass: KClass<ScannerAlertDialog>
            get() = ScannerAlertDialog::class
    }
}