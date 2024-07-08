package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.utils.java.*
import io.opentelemetry.kotlindelegate.utils.java.Function

expect interface Context {

    fun <V> get(key: ContextKey<V>): V?
    fun <V> with(k1: ContextKey<V>, v1: V): Context
    open fun with(value: ImplicitContextKeyed): Context
    open fun wrap(runnable: Runnable): Runnable
    open fun <T, U> wrapFunction(function: Function<T, U>): Function<T, U>
    open fun <T, U, V> wrapFunction(function: BiFunction<T, U, V>): BiFunction<T, U, V>
    open fun <T> wrapConsumer(consumer: Consumer<T>): Consumer<T>
    open fun <T,U> wrapConsumer(consumer: BiConsumer<T, U>): BiConsumer<T, U>
    open fun <T> wrapSupplier(supplier: Supplier<T>): Supplier<T>
}

expect object ContextStatic {

    fun current(): Context
    fun root(): Context
}

expect inline fun <R> Context.runWithActive(crossinline block: ()->R): R
expect suspend inline fun <R> Context.runWithActiveSuspend(crossinline block: suspend ()->R): R
