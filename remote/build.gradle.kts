plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")

}

android {
    compileSdk = Apps.compileSdk
    buildToolsVersion = Apps.buildTools

    defaultConfig {
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions.add("environment")
    productFlavors {
        getOrCreate("dev") {
            dimension = "environment"

            buildConfigField("String", "KAKAO_REST_API_KEY", "\"7165edf50ee98e1383adf5924f5a76ad\"")
            buildConfigField("String", "KAKAO_BASE_URL", "\"https://dapi.kakao.com/\"")
            buildConfigField("String", "NAVER_BASE_URL", "\"https://map.naver.com/\"")
            buildConfigField("String", "DH_BASE_URL", "\"https://www.dhlottery.co.kr/\"")

        }
        getOrCreate("prod") {
            dimension = "environment"

            buildConfigField("String", "KAKAO_REST_API_KEY", "\"7165edf50ee98e1383adf5924f5a76ad\"")
            buildConfigField("String", "KAKAO_BASE_URL", "\"https://dapi.kakao.com/\"")
            buildConfigField("String", "NAVER_BASE_URL", "\"https://map.naver.com/\"")
            buildConfigField("String", "DH_BASE_URL", "\"https://www.dhlottery.co.kr/\"")
        }
    }

    buildTypes {
        getOrCreate("debug") {
            aaptOptions.cruncherEnabled = false

            splits.abi.isEnable = false
            splits.density.isEnable = false

            isJniDebuggable = true
            isRenderscriptDebuggable = true
            isMinifyEnabled = false
            isZipAlignEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getOrCreate("release") {
            splits.abi.isEnable = true

            isJniDebuggable = false
            isRenderscriptDebuggable = false
            isMinifyEnabled = true
            isZipAlignEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":data"))

    implementation(Libs.daggerHiltAndroid)
    kapt(KaptLibs.daggerHiltAndroid)

    implementation(Libs.rxJava)
    api(Libs.gson)
    api(Libs.okHttp)
    implementation(Libs.okHttpLoggingInterceptor)
    api(Libs.retrofit)
    implementation(Libs.retrofitRxJava)
    implementation(Libs.retrofitGson)

    coreLibraryDesugaring(CoreLibs.coreDesugaring)


    androidTestImplementation(AndroidTestLibs.testJunit)
}