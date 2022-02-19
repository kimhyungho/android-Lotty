package com.anseolab.lotty.view.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T> : MutableLiveData<T>() {
    private val hasConsumed = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverWrapper(hasConsumed, observer))
    }

    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(ObserverWrapper(hasConsumed, observer))
    }

    override fun setValue(value: T) {
        hasConsumed.set(false)
        super.setValue(value)
    }

    private class ObserverWrapper<T>(
        private val hasConsumed: AtomicBoolean,
        private val observer: Observer<in T>
    ) : Observer<T> {
        override fun onChanged(t: T) {
            if (hasConsumed.compareAndSet(false, true)) {
                observer.onChanged(t)
            }
        }
    }
}