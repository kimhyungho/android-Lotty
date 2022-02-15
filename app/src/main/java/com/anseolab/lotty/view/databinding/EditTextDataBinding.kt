package com.anseolab.lotty.view.databinding

import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.anseolab.lotty.view.databinding.listener.OnTextChangeListener

@BindingAdapter("onTextChange")
fun EditText.bindOnTextChange(listener: OnTextChangeListener?) {
    this.addTextChangedListener { editable ->
        listener?.onTextChange(editable?.toString() ?: "")
    }
}