package io.opentelemetry.kotlindelegate.context

actual typealias ContextKey<T> = io.opentelemetry.context.ContextKey<T>

actual object ContextKeyStatic {

    actual fun <T> named(name: String): ContextKey<T> = ContextKey.named(name)
}
