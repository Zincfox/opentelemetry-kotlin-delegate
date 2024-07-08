package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.AttributeKey
import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

expect interface SpanBuilder {
    fun setParent(context: Context): SpanBuilder
    fun setNoParent(): SpanBuilder
    fun addLink(spanContext: SpanContext): SpanBuilder
    fun addLink(spanContext: SpanContext, attributes: Attributes): SpanBuilder
    fun setAttribute(key: String, value: String): SpanBuilder
    fun setAttribute(key: String, value: Long): SpanBuilder
    fun setAttribute(key: String, value: Double): SpanBuilder
    fun setAttribute(key: String, value: Boolean): SpanBuilder
    fun <T> setAttribute(key: AttributeKey<T>, value: T): SpanBuilder
    open fun setAllAttributes(attributes: Attributes): SpanBuilder
    fun setSpanKind(spanKind: SpanKind): SpanBuilder
    fun startSpan(): Span
}

expect fun SpanBuilder.setStartTimestampMillis(startTimestampMillis: Long): SpanBuilder
expect fun SpanBuilder.setStartTimestampNanos(startTimestampNanos: Long): SpanBuilder
