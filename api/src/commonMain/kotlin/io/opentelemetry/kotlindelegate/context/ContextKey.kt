package io.opentelemetry.kotlindelegate.context

expect interface ContextKey<V>

expect object ContextKeyStatic {
    fun <T> named(name: String): ContextKey<T>
}
