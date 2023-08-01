package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.context.ContextKey
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ContextKeyWrapper<T>(
    override val wrappedObject: ContextKey<T>
) : WrapperBase<ContextKey<T>>(), IWrapper {
    actual companion object {

        actual fun <T> named(name: String): ContextKeyWrapper<T> {
            return ContextKeyWrapper(ContextKey.named(name))
        }
    }
}
