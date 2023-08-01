package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class ContextStorageProviderWrapper : IWrapper {

    fun get(): ContextStorageWrapper
}
