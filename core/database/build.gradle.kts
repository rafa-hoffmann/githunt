@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("githunt.android.library")
    id("githunt.android.library.jacoco")
    id("githunt.android.hilt")
    id("githunt.android.room")
}

android {
    defaultConfig {
        testInstrumentationRunner =
            "com.sonder.githunt.core.testing.TestRunner"
    }
    namespace = "com.sonder.githunt.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.pagination)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.room.paging)

    androidTestImplementation(project(":core:testing"))
}
