package io.opentelemetry.kotlindelegate.context

actual typealias ImplicitContextKeyed = io.opentelemetry.context.ImplicitContextKeyed

actual inline fun <R> ImplicitContextKeyed.runWithActive(block: () -> R): R {
    return makeCurrent().use { block() }
}
