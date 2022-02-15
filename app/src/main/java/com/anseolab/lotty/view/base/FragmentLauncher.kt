package com.anseolab.lotty.view.base

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

abstract class FragmentLauncher<T: Fragment> {
    abstract val fragmentClass: KClass<T>
    val name: String = fragmentClass.qualifiedName ?: ""

    open fun getInstance(): T{
        return fragmentClass.java.newInstance()
    }
}