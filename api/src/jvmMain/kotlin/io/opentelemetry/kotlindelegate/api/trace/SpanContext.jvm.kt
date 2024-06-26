package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.TraceState

actual typealias SpanContext = io.opentelemetry.api.trace.SpanContext

actual object SpanContextStatic {

    actual fun getInvalid(): SpanContext = SpanContext.getInvalid()

    actual fun create(
        traceIdHex: String,
        spanIdHex: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
    ): SpanContext = SpanContext.create(spanIdHex, traceIdHex, traceFlags, traceState)

    actual fun createFromRemoteParent(
        traceIdHex: String,
        spanIdHex: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
    ): io.opentelemetry.api.trace.SpanContext =
        SpanContext.createFromRemoteParent(traceIdHex, spanIdHex, traceFlags, traceState)
}
