package com.anseolab.lotty.view.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.NavHostFragment
import com.anseolab.lotty.R
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseActivity<VD : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : AppCompatActivity() {

    private var backKeyPressedTime: Long = 0

    protected val compositeDisposable = CompositeDisposable()

    private var _viewDataBinding: VD? = null
    protected val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    private val _navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment)?.navController
    }
    protected val navController get() = _navController!!

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<VD>(this, layoutResId).also { viewDataBinding ->
            viewDataBinding.lifecycleOwner = this
            this._viewDataBinding = viewDataBinding
        }
    }

    override fun onBackPressed() {
        if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
            return
        }

        if (_navController?.popBackStack() == false) {
            finish()
            return
        }

        super.onBackPressed()
    }

    override fun onDestroy() {
        _viewDataBinding = null
        compositeDisposable.clear()
        super.onDestroy()
    }
}