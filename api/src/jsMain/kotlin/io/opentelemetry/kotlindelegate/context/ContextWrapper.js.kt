package io.opentelemetry.kotlindelegate.context

actual class ContextWrapper : IWrapper {

    actual operator fun <V> get(key: ContextKeyWrapper<V>): V? {
        TODO("Not yet implemented")
    }

    actual fun <V: Any> with(
        k1: ContextKeyWrapper<V>,
        v1: V,
    ): ContextWrapper {
        TODO("Not yet implemented")
    }

    actual fun makeCurrent(): ScopeWrapper {
        TODO("Not yet implemented")
    }

    actual companion object {

        actual fun current(): ContextWrapper {
            TODO("Not yet implemented")
        }

        actual fun root(): ContextWrapper {
            TODO("Not yet implemented")
        }
    }
}
