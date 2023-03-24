plugins {
    id("githunt.android.feature")
    id("githunt.android.library.jacoco")
}

android {
    namespace = "com.sonder.githunt.feature.repository_list"

    viewBinding {
        enable = true
    }
}
