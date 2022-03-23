object Apps {
    const val compileSdk = 31
    const val buildTools = "30.0.3"
    const val applicationId = "com.anseolab.lotty"
    const val minSdk = 23
    const val targetSdk = 31
    const val versionCode = 4
    const val versionName = "1.0.0"
}

object Versions {
    const val kotlin = "1.6.0"
    const val daggerHiltAndroid = "2.40.5"
    const val coreKts = "1.6.0"
    const val appcompat = "1.4.0"
    const val material = "1.4.0"
    const val constraintLayout = "2.1.2"
    const val junit = "4.+"
    const val testJunit = "1.1.3"
    const val espresso = "3.4.0"
    const val leakCanary = "2.8.1"
    const val navigation = "2.4.0"
    const val rxAndroid = "3.0.0"
    const val rxJava = "3.0.12"
    const val glide = "4.12.0"
    const val glideTransformations ="4.3.0"
    const val rxBinding = "4.0.0"
    const val retrofit = "2.9.0"
    const val okHttp = "4.9.1"
    const val gson = "2.8.7"
    const val coreDesugaring = "1.1.0"
    const val naverMap = "3.13.0"
    const val tedPermission = "3.3.0"
    const val gmsLocation ="19.0.1"
}

object Libs {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKts}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val daggerHiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerHiltAndroid}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideTransformations = "jp.wasabeef:glide-transformations:${Versions.glideTransformations}"

    const val rxBinding = "com.jakewharton.rxbinding4:rxbinding:${Versions.rxBinding}"
    const val rxBindingAppCompat = "com.jakewharton.rxbinding4:rxbinding-appcompat:${Versions.rxBinding}"
    const val rxBindingViewPager2 = "com.jakewharton.rxbinding4:rxbinding-viewpager2:${Versions.rxBinding}"
    const val rxBindingSwipeRefreshLayout = "com.jakewharton.rxbinding4:rxbinding-swiperefreshlayout:${Versions.rxBinding}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val gmsLocation ="com.google.android.gms:play-services-location:${Versions.gmsLocation}"
    const val naverMap = "com.naver.maps:map-sdk:${Versions.naverMap}"

    const val tedPermission = "io.github.ParkSangGwon:tedpermission-rx3:${Versions.tedPermission}"
}



object KaptLibs {
    const val daggerHiltAndroid = "com.google.dagger:hilt-android-compiler:${Versions.daggerHiltAndroid}"
    const val glide = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object DebugLibs {
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    const val plumberAndroid = "com.squareup.leakcanary:plumber-android:${Versions.leakCanary}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
}

object AndroidTestLibs {
    const val testJunit = "androidx.test.ext:junit:${Versions.testJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object CoreLibs {
    const val coreDesugaring = "com.android.tools:desugar_jdk_libs:${Versions.coreDesugaring}"
}