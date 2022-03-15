package com.anseolab.lotty.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.anseolab.lotty.R
import com.anseolab.lotty.utils.gilde.GlideUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<VD : ViewDataBinding>(
    @LayoutRes
    protected val layoutResId: Int
) : Fragment() {
    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    protected var _viewDataBinding: VD? = null
    protected val viewCompositeDisposable = CompositeDisposable()
    protected val viewDataBinding: VD
        get() = _viewDataBinding ?: throw IllegalStateException("viewDataBinding can not be null")
    protected val navController by lazy { findNavController() }

    protected open var isBackPressDispatcherEnabled
        get() = backPressedCallback.isEnabled
        set(value) {
            backPressedCallback.isEnabled = value
        }

    @IdRes
    protected open val toolBarId: Int = R.id.toolbar

    @DrawableRes
    protected open val backButtonResId: Int = R.drawable.ic_back_24x24
    protected open val showBackButton: Boolean = false

    @MenuRes
    protected open val menuId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
            .also { viewDataBinding ->
                viewDataBinding.lifecycleOwner = viewLifecycleOwner
                this._viewDataBinding = viewDataBinding
            }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

        view.findViewById<Toolbar>(toolBarId)?.let { toolbar ->
            toolbar.title = null

            if (showBackButton) {
                toolbar.setNavigationIcon(backButtonResId)
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }

            if (menuId != 0) {
                toolbar.inflateMenu(menuId)
            }
        }
    }


    override fun onDestroyView() {
        viewCompositeDisposable.clear()
        GlideUtil.clear(viewDataBinding.root)
        _viewDataBinding = null
        super.onDestroyView()
    }

    open fun onBackPressed() {
        navController.popBackStack()
    }

    protected fun <T : Any> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, {
            try {
                observer.invoke(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    protected inline fun <T : Any> Observable<T>.bind(
        crossinline onNext: (value: T) -> Unit
    ) {
        subscribe({ onNext.invoke(it) }, { it.printStackTrace() })
            .let(viewCompositeDisposable::add)
    }
}