package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.extension.kotlin.asContextElement
import io.opentelemetry.extension.kotlin.getOpenTelemetryContext as getOpenTelemetryContextJvm
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

actual typealias Context = io.opentelemetry.context.Context

actual object ContextStatic {

    actual fun current(): Context = Context.current()

    actual fun root(): Context = Context.root()
}

actual fun CoroutineContext.getOpenTelemetryContext(): Context = getOpenTelemetryContextJvm()

actual inline fun <R> Context.runWithActive(crossinline block: () -> R): R {
    return makeCurrent().use { block() }
}

actual suspend inline fun <R> Context.runWithActiveSuspend(crossinline block: suspend () -> R): R {
    return withContext(this.asContextElement()) {block()}
}
