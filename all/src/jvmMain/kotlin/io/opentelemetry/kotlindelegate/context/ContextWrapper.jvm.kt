package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.context.Context
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class ContextWrapper(override val wrappedObject: Context) : WrapperBase<Context>(), IWrapper {

    actual operator fun <V> get(key: ContextKeyWrapper<V>): V? {
        return wrappedObject.get(key.wrappedObject)
    }

    actual fun <V: Any> with(
        k1: ContextKeyWrapper<V>,
        v1: V,
    ): ContextWrapper {
        return wrappedObject.with(k1.wrappedObject,v1).letWrapImmutable(::ContextWrapper)
    }

    actual fun makeCurrent(): ScopeWrapper {
        return wrappedObject.makeCurrent().letWrapImmutable(::ScopeWrapper)
    }

    actual companion object {

        actual fun current(): ContextWrapper {
            return Context.current().letWrapImmutable(::ContextWrapper)
        }

        actual fun root(): ContextWrapper {
            return Context.root().letWrapImmutable(::ContextWrapper)
        }
    }
}
