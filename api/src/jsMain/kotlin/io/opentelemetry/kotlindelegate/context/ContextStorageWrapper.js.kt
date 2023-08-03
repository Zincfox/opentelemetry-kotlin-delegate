package io.opentelemetry.kotlindelegate.context

actual class ContextStorageWrapper : IWrapper {
    actual companion object {

        actual fun get(): ContextStorageWrapper {
            TODO("Not yet implemented")
        }

        actual fun defaultStorage(): ContextStorageWrapper {
            TODO("Not yet implemented")
        }
    }

    actual fun attach(toAttach: ContextWrapper): ScopeWrapper {
        TODO("Not yet implemented")
    }

    actual fun current(): ContextWrapper? {
        TODO("Not yet implemented")
    }

    actual fun root(): ContextWrapper {
        TODO("Not yet implemented")
    }
}
