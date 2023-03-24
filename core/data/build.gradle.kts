plugins {
    id("githunt.android.library")
    id("githunt.android.library.jacoco")
    id("githunt.android.hilt")
    id("kotlinx-serialization")
    id("githunt.android.room")
}

android {
    namespace = "com.sonder.githunt.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))

    testImplementation(project(":core:testing"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.pagination)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
