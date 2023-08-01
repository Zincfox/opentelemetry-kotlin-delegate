package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.context.ContextStorageProvider
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ContextStorageProviderWrapper(
    override val wrappedObject: ContextStorageProvider,
) : WrapperBase<ContextStorageProvider>(), IWrapper {

    actual fun get(): ContextStorageWrapper {
        return ContextStorageWrapper(wrappedObject.get())
    }
}
