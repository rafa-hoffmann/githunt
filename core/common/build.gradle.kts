plugins {
    id("githunt.android.library")
    id("githunt.android.library.jacoco")
    id("githunt.android.hilt")
}

android {
    namespace = "com.sonder.githunt.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(project(":core:testing"))
}
