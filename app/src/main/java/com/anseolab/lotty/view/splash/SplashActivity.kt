package com.anseolab.lotty.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.anseolab.lotty.MainActivity
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.ActivitySplashBinding
import com.anseolab.lotty.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(
    R.layout.activity_splash
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
    }
}