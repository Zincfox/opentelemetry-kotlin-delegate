package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.context.ContextStorage
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class ContextStorageWrapper(
    override val wrappedObject: ContextStorage,
) : WrapperBase<ContextStorage>(), IWrapper {
    actual companion object {

        actual fun get(): ContextStorageWrapper {
            return ContextStorage.get().letWrapImmutable(::ContextStorageWrapper)
        }

        actual fun defaultStorage(): ContextStorageWrapper {
            return ContextStorage.defaultStorage().letWrapImmutable(::ContextStorageWrapper)
        }

        actual fun addWrapper(wrapper: (ContextStorageWrapper) -> ContextStorageWrapper) {
            return ContextStorage.addWrapper {
                wrapper(it.let(::ContextStorageWrapper)).wrappedObject
            }
        }
    }

    actual fun attach(toAttach: ContextWrapper): ScopeWrapper {
        return wrappedObject.attach(toAttach.wrappedObject).letWrapImmutable(::ScopeWrapper)
    }

    actual fun current(): ContextWrapper? {
        return wrappedObject.current()?.letWrapImmutable(::ContextWrapper)
    }

    actual fun root(): ContextWrapper {
        return wrappedObject.root().letWrapImmutable(::ContextWrapper)
    }
}
