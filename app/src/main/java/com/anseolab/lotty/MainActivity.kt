package com.anseolab.lotty

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anseolab.lotty.databinding.ActivityMainBinding
import com.anseolab.lotty.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import java.security.NoSuchAlgorithmException


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController.setGraph(R.navigation.nav_main)

    }
}