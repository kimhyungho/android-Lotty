package com.anseolab.lotty.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.anseolab.lotty.BR
import com.anseolab.lotty.R
import com.anseolab.lotty.utils.gilde.GlideUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class ViewModelFragment<VD : ViewDataBinding, VM : ViewModelType<*, *>>(
    @LayoutRes
    private val layoutResId: Int
) : Fragment(), ViewModelLifeCycle<VD, VM> {
    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    protected val compositeDisposable = CompositeDisposable()

    protected abstract val viewModel: VM

    private var _viewDataBinding: VD? = null
    protected val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    protected open var isBackPressDispatcherEnabled
        get() = backPressedCallback.isEnabled
        set(value) {
            backPressedCallback.isEnabled = value
        }

//    @IdRes
//    protected open val toolbarId: Int = R.id.toolbar

    @DrawableRes
    open val backButtonResId: Int = R.drawable.ic_back_24x24

    protected open val showBackButton: Boolean = false

    val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
            .also { viewDataBinding ->
                onWillAttachViewModel(viewDataBinding, viewModel)

                viewDataBinding.lifecycleOwner = this
                viewDataBinding.setVariable(BR.input, viewModel.input)
                viewDataBinding.setVariable(BR.output, viewModel.output)
                this._viewDataBinding = viewDataBinding
                onDidAttachViewModel(viewDataBinding, viewModel)
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

//        view.findViewById<Toolbar>(toolbarId)?.let { toolbar ->
//            toolbar.title = null
//
//            if (showBackButton) {
//                toolbar.setNavigationIcon(backButtonResId)
//                toolbar.setNavigationOnClickListener {
//                    onBackPressed()
//                }
//            }
//        }
    }

    protected open fun onBackPressed() {
        navController.popBackStack()
    }


    override fun onDestroyView() {
        onWillDetachViewModel(viewDataBinding, viewModel)

        GlideUtil.clear(viewDataBinding.root)
        _viewDataBinding = null
        compositeDisposable.clear()

        onDidDetachViewModel()
        super.onDestroyView()
    }

    protected inline fun <T : Any> Observable<T>.bind(
        crossinline onNext: (value: T) -> Unit
    ) {
        subscribe({ onNext.invoke(it) }, { it.printStackTrace() })
            .let(compositeDisposable::add)
    }

    protected fun <T: Any> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, {
            try{
                observer.invoke(it!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

    }
}