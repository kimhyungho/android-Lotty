package com.anseolab.lotty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anseolab.lotty.databinding.ActivityMainBinding
import com.anseolab.lotty.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController.setGraph(R.navigation.nav_main)
    }
}