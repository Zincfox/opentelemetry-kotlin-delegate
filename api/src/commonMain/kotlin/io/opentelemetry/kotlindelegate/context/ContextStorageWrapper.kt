package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class ContextStorageWrapper : IWrapper {
    companion object {

        fun get(): ContextStorageWrapper
        fun defaultStorage(): ContextStorageWrapper
        fun addWrapper(wrapper: (ContextStorageWrapper) -> ContextStorageWrapper)
    }

    fun attach(toAttach: ContextWrapper): ScopeWrapper
    fun current(): ContextWrapper?
    fun root(): ContextWrapper
}
