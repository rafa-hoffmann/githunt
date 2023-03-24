import com.sonder.githunt.BuildType

plugins {
    id("githunt.android.application")
    id("githunt.android.application.flavors")
    id("githunt.android.application.jacoco")
    id("githunt.android.hilt")
    id("jacoco")
}

android {
    defaultConfig {
        applicationId = "com.sonder.githunt"
        versionCode = 5
        versionName = "0.0.1"

        testInstrumentationRunner = "com.sonder.githunt.core.testing.TestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    viewBinding {
        enable = true
    }

    namespace = "com.sonder.githunt"
}

dependencies {
    implementation(project(":feature:repository-list"))

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(project(":core:data-test"))
    androidTestImplementation(project(":core:network"))
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(kotlin("test"))
    debugImplementation(project(":ui-test-hilt-manifest"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.material)

    implementation(libs.coil.kt)
}

configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
        // Temporary workaround for https://issuetracker.google.com/174733673
        force("org.objenesis:objenesis:2.6")
    }
}
