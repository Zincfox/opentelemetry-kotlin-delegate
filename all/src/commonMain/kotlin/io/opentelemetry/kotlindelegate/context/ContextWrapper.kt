package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class ContextWrapper : IWrapper {

    operator fun <V> get(key: ContextKeyWrapper<V>): V?

    fun <V: Any> with(k1: ContextKeyWrapper<V>, v1: V): ContextWrapper

    fun makeCurrent(): ScopeWrapper

    companion object {

        fun current(): ContextWrapper
        fun root(): ContextWrapper
    }
}

fun ContextWrapper.with(value: ImplicitContextKeyedWrapper): ContextWrapper = value.storeInContext(this)

inline fun <T> ContextWrapper.wrap(crossinline block: () -> T): () -> T = {
    val scope: ScopeWrapper = makeCurrent()
    try {
        block()
    } finally {
        scope.close()
    }
}

inline fun <T, U> ContextWrapper.wrap(crossinline block: (T) -> U): (T) -> U = {
    val scope: ScopeWrapper = makeCurrent()
    try {
        block(it)
    } finally {
        scope.close()
    }
}

inline fun <T, U, V> ContextWrapper.wrap(crossinline block: (T, U) -> V): (T, U) -> V = { t, u ->
    val scope: ScopeWrapper = makeCurrent()
    try {
        block(t, u)
    } finally {
        scope.close()
    }
}

inline fun <T> ContextWrapper.wrap(crossinline block: (T) -> Unit): (T) -> Unit = wrap<T, Unit>(block)
inline fun <T, U> ContextWrapper.wrap(crossinline block: (T, U) -> Unit): (T, U) -> Unit = wrap<T, U, Unit>(block)
inline fun ContextWrapper.wrap(crossinline block: () -> Unit): () -> Unit = wrap<Unit>(block)
