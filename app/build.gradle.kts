import org.jetbrains.kotlin.gradle.targets.js.npm.includedRange

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(31)
    buildToolsVersion("30.0.3")

    signingConfigs {
        getOrCreate("lotty.debug") {
            keyAlias = "lotty"
            keyPassword = "lotty220215"
            storeFile = file("../DebugKeyStore")
            storePassword = "lotty220215"
        }
        getOrCreate("lotty") {
            keyAlias = "lotty"
            keyPassword = "lotty220215"
            storeFile = file("../Lotty")
            storePassword = "lotty220215"
        }
    }

    defaultConfig {
        applicationId = Apps.applicationId
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions("environment")
    productFlavors {
        getOrCreate("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"

            addManifestPlaceholders(
                mapOf(
                    "NAVER_CLOUD_CLIENT_ID" to "a7nivesl0b",
                    "NAVER_CLOUD_CLIENT_SECRET" to "RpkSLo0AQMucOyftm3VEFjO0NQYLzM9edmnD9xv6"
                )
            )
        }

        getOrCreate("prod") {
            dimension = "environment"

            addManifestPlaceholders(
                mapOf(
                    "NAVER_CLOUD_CLIENT_ID" to "a7nivesl0b",
                    "NAVER_CLOUD_CLIENT_SECRET" to "RpkSLo0AQMucOyftm3VEFjO0NQYLzM9edmnD9xv6"
                )
            )
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
            isJniDebuggable = false
        }
        getOrCreate("debug") {
            aaptOptions.cruncherEnabled = false

            splits.abi.isEnable = false
            splits.density.isEnable = false

            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getOrCreate("release") {
            splits.abi.isEnable = true

            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false

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

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":remote"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":local"))

    implementation(Libs.daggerHiltAndroid)
    kapt(KaptLibs.daggerHiltAndroid)

    debugImplementation(DebugLibs.leakCanary)
    implementation(DebugLibs.plumberAndroid)

    implementation(Libs.coreKtx)
    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    testImplementation(TestLibs.junit)
    androidTestImplementation(AndroidTestLibs.testJunit)
    androidTestImplementation(AndroidTestLibs.espresso)

    implementation(Libs.navigationFragment)
    implementation(Libs.navigationUi)

    implementation(Libs.rxAndroid)

    implementation (Libs.glide)
    implementation(Libs.glideTransformations)

    kapt(KaptLibs.glide)

    implementation(Libs.rxBinding)
    implementation(Libs.rxBindingAppCompat)
    implementation(Libs.rxBindingViewPager2)
    implementation(Libs.rxBindingSwipeRefreshLayout)

    implementation(Libs.naverMap)

    implementation (Libs.tedPermission)

    coreLibraryDesugaring(CoreLibs.coreDesugaring)

    implementation(Libs.gmsLocation)

    implementation ("nl.dionsegijn:konfetti-compose:2.0.2")
    implementation ("nl.dionsegijn:konfetti-xml:2.0.2")

    implementation ("com.github.yuriy-budiyev:code-scanner:2.1.0")

//    implementation("androidx.dynamicanimation:dynamicanimation-ktx:1.1.0-alpha03")\

    implementation ("com.robinhood.ticker:ticker:2.0.4")

    implementation("xyz.pinaki.android:wheelticker:1.0.1")

    implementation("androidx.core:core-splashscreen:1.0.0-beta01")


}