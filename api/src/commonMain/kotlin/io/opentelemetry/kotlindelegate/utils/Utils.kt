package io.opentelemetry.kotlindelegate.utils

import io.opentelemetry.kotlindelegate.api.trace.SpanWrapper
import io.opentelemetry.kotlindelegate.api.trace.StatusCodeWrapper

internal inline fun <reified T, reified R : WrapperBase<in T>> T.letWrapImmutable(block: T.() -> R): R = let(block)

fun <T> cachedWriteOnly(initial: T, setter: (T) -> Unit): CachedWriteOnlyAdapter<T> =
    CachedWriteOnlyAdapter(initial, setter)

fun <T:Nothing?> cachedWriteOnly(initial: T?=null, setter: (T?) -> Unit): CachedWriteOnlyAdapter<T?> =
    CachedWriteOnlyAdapter(initial, setter)

fun <T> runInSpanDefaultOnError(span: SpanWrapper, error: Throwable): T {
    span.recordException(error)
    span.setStatus(StatusCodeWrapper.ERROR)
    throw error
}
