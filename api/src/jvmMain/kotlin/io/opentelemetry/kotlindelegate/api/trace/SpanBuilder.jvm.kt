package io.opentelemetry.kotlindelegate.api.trace

import java.util.concurrent.TimeUnit

actual typealias SpanBuilder = io.opentelemetry.api.trace.SpanBuilder

actual fun SpanBuilder.setStartTimestampMillis(startTimestampMillis: Long): SpanBuilder =
    setStartTimestamp(startTimestampMillis, TimeUnit.MILLISECONDS)

actual fun SpanBuilder.setStartTimestampNanos(startTimestampNanos: Long): SpanBuilder =
    setStartTimestamp(startTimestampNanos, TimeUnit.NANOSECONDS)
