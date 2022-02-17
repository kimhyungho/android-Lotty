package com.anseolab.lotty.view.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

interface ViewHolderLifeCycle<E: Any>: LifecycleOwner {
    val lifecycleRegistry: LifecycleRegistry

    fun onBind(item: E)

    fun onRecycle() {

    }

    fun onAttach() {
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    fun onDetach() {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun getLifecycle(): Lifecycle = this.lifecycleRegistry
}