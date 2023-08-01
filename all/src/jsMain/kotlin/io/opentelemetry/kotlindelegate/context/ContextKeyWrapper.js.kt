package io.opentelemetry.kotlindelegate.context

actual class ContextKeyWrapper<T> : IWrapper {
    actual companion object {

        actual fun <T> named(name: String): ContextKeyWrapper<T> {
            TODO("Not yet implemented")
        }
    }
}
