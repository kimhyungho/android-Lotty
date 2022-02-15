package com.anseolab.lotty.extensions

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentTransaction.hideAll(
    fragmentManager: FragmentManager
): FragmentTransaction {
    return this.apply {
        for (fragment in fragmentManager.fragments) {
            hide(fragment)
        }
    }
}