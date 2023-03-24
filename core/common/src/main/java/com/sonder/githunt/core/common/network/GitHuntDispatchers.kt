package com.sonder.githunt.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: GitHuntDispatchers)

enum class GitHuntDispatchers {
    IO
}
