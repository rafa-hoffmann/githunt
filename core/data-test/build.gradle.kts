plugins {
    id("githunt.android.library")
    id("githunt.android.hilt")
}

android {
    namespace = "com.sonder.githunt.core.data.test"
}

dependencies {
    api(project(":core:data"))
    implementation(project(":core:testing"))
}
