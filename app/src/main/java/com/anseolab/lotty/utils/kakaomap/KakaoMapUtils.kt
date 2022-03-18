package com.anseolab.lotty.utils.kakaomap

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.anseolab.lotty.view.main.around.AroundFragment

object KakaoMapUtils {
    const val KAKAO_MAP_PACKAGE_NAME = "net.daum.android.map"
    const val KAKAO_MAP_DOWNLOAD_PAGE =
        "https://play.google.com/store/apps/details?id=net.daum.android.map"

    fun checkInstalledKakaoMap(context: Context): Boolean {
        val pm = context.packageManager

        return try {
            pm.getPackageInfo(KAKAO_MAP_PACKAGE_NAME, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun openPlayStoreForKakaoMap(context: Context) {
        startActivity(
            context,
            Intent(Intent.ACTION_VIEW, Uri.parse(KAKAO_MAP_DOWNLOAD_PAGE)),
            null
        )
    }

    fun openKakaoMapForRoute(
        context: Context,
        sLat: Double,
        sLong: Double,
        dLat: Double,
        dLong: Double
    ) {
        val url =
            Uri.parse("kakaomap://route?sp=${sLat},${sLong}&ep=${dLat},${dLong}&by=CAR")
        startActivity(context, Intent(Intent.ACTION_VIEW, url), null)
    }

    fun openKakaoMapForSearch(
        context: Context,
        query: String,
        latitude: Double,
        longitude: Double
    ) {
        val url = Uri.parse("kakaomap://search?q=$query&p=$latitude,$longitude")
        startActivity(context, Intent(Intent.ACTION_VIEW, url), null)
    }

}