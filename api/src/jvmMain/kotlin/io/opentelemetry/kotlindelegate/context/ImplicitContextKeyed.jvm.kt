package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.extension.kotlin.asContextElement
import kotlinx.coroutines.withContext

actual typealias ImplicitContextKeyed = io.opentelemetry.context.ImplicitContextKeyed

actual inline fun <R> ImplicitContextKeyed.runWithActive(block: () -> R): R {
    return makeCurrent().use { block() }
}

actual suspend inline fun <R> ImplicitContextKeyed.runWithActiveSuspend(
    crossinline block: suspend () -> R,
): R {
    return withContext(this.asContextElement()) {block()}
}
