// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHiltAndroid}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://jitpack.io")
        maven {
            url = uri("https://naver.jfrog.io/artifactory/maven/")
        }
        maven{
            url = uri("https://devrepo.kakao.com/nexus/content/groups/public/")
        }

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}