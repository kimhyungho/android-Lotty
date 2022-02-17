package com.anseolab.lotty.view.databinding

import android.view.MenuItem
import androidx.databinding.BindingAdapter
import com.anseolab.lotty.R
import com.anseolab.lotty.view.databinding.listener.OnPageSelectListener
import com.google.android.material.bottomnavigation.BottomNavigationView

@BindingAdapter("selectPage")
fun BottomNavigationView.bindSelectPage(listener: OnPageSelectListener?) {
    this.setOnItemSelectedListener {
        val page = convertMainMenuToPage(it)
        listener?.onPageSelect(page)
        true
    }
}

@BindingAdapter("page")
fun BottomNavigationView.bindPage(page: Int) {
    this.selectedItemId = convertPageToMenuItem(page)
}

fun convertMainMenuToPage(menuItem: MenuItem): Int {
    return when(menuItem.itemId) {
        R.id.menu_around -> 0
        R.id.menu_search -> 1
        R.id.menu_random -> 2
        R.id.menu_setting -> 3
        else -> 0
    }
}

fun convertPageToMenuItem(page: Int): Int {
    return when(page) {
        0 -> R.id.menu_around
        1 -> R.id.menu_search
        2 -> R.id.menu_random
        3 -> R.id.menu_setting
        else -> R.id.menu_around
    }
}
