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
import com.anseolab.lotty.R
import com.anseolab.lotty.utils.gilde.GlideUtil
import io.reactivex.rxjava3.core.Observable

abstract class BaseDialogFragment<VD : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.Theme_Lotty_Dialog)
        return super.onCreateDialog(savedInstanceState)
    }

    private var _viewDataBinding: VD? = null
    protected val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
            .also { viewDataBinding ->
                viewDataBinding.lifecycleOwner = this
                this._viewDataBinding = viewDataBinding
            }.root
    }

    protected inline fun <T : Any> Observable<T>.bind(
        crossinline onNext: (value: T) -> Unit
    ) {
        this.subscribe({ onNext.invoke(it) }, { it.printStackTrace() })
    }

    override fun onDestroyView() {
        GlideUtil.clear(viewDataBinding.root)
        _viewDataBinding = null

        super.onDestroyView()
    }

    protected open fun onBackPressed() {
        requireActivity().onBackPressed()
    }

}