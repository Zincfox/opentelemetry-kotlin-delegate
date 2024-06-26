package io.opentelemetry.kotlindelegate.utils

fun <T> cachedWriteOnly(initial: T, setter: (T) -> Unit): CachedWriteOnlyAdapter<T> =
    CachedWriteOnlyAdapter(initial, setter)

fun <T:Nothing?> cachedWriteOnly(initial: T?=null, setter: (T?) -> Unit): CachedWriteOnlyAdapter<T?> =
    CachedWriteOnlyAdapter(initial, setter)
