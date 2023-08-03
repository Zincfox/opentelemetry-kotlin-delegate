package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.runInSpanDefaultOnError

expect class TracerWrapper : IWrapper {

    fun spanBuilder(spanName: String): SpanBuilderWrapper
    inline fun <T> runInSpan(
        spanName: String,
        onError: (SpanWrapper, Throwable) -> T = ::runInSpanDefaultOnError,
        block: () -> T,
    ): T
}

inline fun <T> TracerWrapper.runInSpan(
    spanName: String,
    configuration: SpanBuilderWrapper.() -> Unit,
    onError: (SpanWrapper, Throwable) -> T = ::runInSpanDefaultOnError,
    crossinline block: () -> T,
): T = spanBuilder(spanName).apply(configuration).runInside(onError, block)
