package com.anseolab.lotty.view.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import com.anseolab.lotty.BR
import com.anseolab.lotty.R
import com.anseolab.lotty.utils.gilde.GlideUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class ViewModelDialogFragment<VD : ViewDataBinding, VM : ViewModelType<*, *>>(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment(), ViewModelLifeCycle<VD, VM> {
    protected val compositeDisposable = CompositeDisposable()

    abstract val viewModel: VM

    private var _viewDataBinding: VD? = null
    protected val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.Theme_Lotty_Dialog)
        return super.onCreateDialog(savedInstanceState)
    }

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

    protected inline fun <T : Any> Observable<T>.bind(
        crossinline onNext: (value: T) -> Unit
    ) {
        this.subscribe({ onNext.invoke(it) }, { it.printStackTrace() })
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

    override fun onDestroyView() {
        onWillDetachViewModel(viewDataBinding, viewModel)

        GlideUtil.clear(viewDataBinding.root)
        _viewDataBinding = null
        compositeDisposable.clear()

        onDidDetachViewModel()
        super.onDestroyView()
    }

    protected open fun onBackPressed() {
        requireActivity().onBackPressed()
    }
}