package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.*
import io.opentelemetry.kotlindelegate.context.*

expect interface Span : ImplicitContextKeyed {
    open fun setAttribute(key: String, value: String): Span
    open fun setAttribute(key: String, value: Long): Span
    open fun setAttribute(key: String, value: Double): Span
    open fun setAttribute(key: String, value: Boolean): Span
    fun <T> setAttribute(key: AttributeKey<T>, value: T): Span
    open fun setAttribute(key: AttributeKey<Long>, value: Int): Span
    open fun setAllAttributes(attributes: Attributes): Span
    open fun addEvent(name: String): Span
    fun addEvent(name: String, attributes: Attributes): Span
    open fun setStatus(statusCode: StatusCode): Span
    fun setStatus(statusCode: StatusCode, description: String): Span
    open fun recordException(exception: Throwable): Span
    fun recordException(exception: Throwable, additionalAttributes: Attributes): Span
    fun updateName(name: String): Span
    fun end()
    fun getSpanContext(): SpanContext
    fun isRecording(): Boolean
    open override fun storeInContext(context: Context): Context
}

expect fun Span.addEventMillis(name: String, timestampMillis: Long): Span
expect fun Span.addEventMillis(name: String, attributes: Attributes, timestampMillis: Long): Span
expect fun Span.addEventNanos(name: String, timestampNanos: Long): Span
expect fun Span.addEventNanos(name: String, attributes: Attributes, timestampNanos: Long): Span
expect fun Span.endMillis(timestampMillis: Long)
expect fun Span.endNanos(timestampNanos: Long)

expect object SpanStatic {
    fun current(): Span
    fun fromContext(context: Context): Span
    fun fromContextOrNull(context: Context): Span?
    fun getInvalid(): Span
    fun wrap(spanContext: SpanContext): Span
}
