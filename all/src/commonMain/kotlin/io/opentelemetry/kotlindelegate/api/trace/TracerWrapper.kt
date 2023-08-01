package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class TracerWrapper : IWrapper {

    fun spanBuilder(spanName: String): SpanBuilderWrapper
    inline fun <T> runInSpan(
        spanName: String,
        onException: (SpanWrapper, Throwable) -> Unit = { span, ex ->
            span.recordException(ex)
            span.setStatus(StatusCodeWrapper.ERROR)
        },
        block: () -> T,
    ): T
}

inline fun <T> TracerWrapper.runInSpan(
    spanName: String,
    configuration: SpanBuilderWrapper.() -> Unit,
    onException: (SpanWrapper, Throwable) -> Unit = { span, ex ->
        span.recordException(ex)
        span.setStatus(StatusCodeWrapper.ERROR)
    },
    crossinline block: () -> T,
): T = spanBuilder(spanName).apply(configuration).runInside(onException, block)
