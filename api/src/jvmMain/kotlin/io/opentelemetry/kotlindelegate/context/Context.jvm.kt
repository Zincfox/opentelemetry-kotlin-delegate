package io.opentelemetry.kotlindelegate.context

actual typealias Context = io.opentelemetry.context.Context

actual object ContextStatic {

    actual fun current(): Context = Context.current()

    actual fun root(): Context = Context.root()
}

actual inline fun <R> Context.runWithActive(crossinline block: () -> R): R {
    return makeCurrent().use { block() }
}
