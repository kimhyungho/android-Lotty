plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")

}

android {
    compileSdkVersion(Apps.compileSdk)
    buildToolsVersion(Apps.buildTools)

    defaultConfig {
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions("environment")
    productFlavors {
        getOrCreate("dev") {
            dimension = "environment"

            buildConfigField("String", "OD_CLOUD_BASE_URL", "\"api.odcloud.kr/api/\"")
            buildConfigField("String", "DH_BASE_URL", "\"https://www.dhlottery.co.kr/\"")

        }
        getOrCreate("prod") {
            dimension = "environment"

            buildConfigField("String", "OD_CLOUD_BASE_URL", "\"api.odcloud.kr/api/\"")
            buildConfigField("String", "DH_BASE_URL", "\"www.dhlottery.co.kr/\"")

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