package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.AttributeKeyWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyedWrapper
import kotlin.time.DurationUnit

expect class SpanWrapper : ImplicitContextKeyedWrapper {
    companion object {

        fun current(): SpanWrapper
        fun fromContext(context: ContextWrapper): SpanWrapper
        fun fromContextOrNull(context: ContextWrapper): SpanWrapper?
        val invalid: SpanWrapper
        fun wrap(spanContext: SpanContextWrapper): SpanWrapper
    }

    fun setAttribute(key: String, value: String): SpanWrapper
    fun setAttribute(key: String, value: Long): SpanWrapper
    fun setAttribute(key: String, value: Double): SpanWrapper
    fun setAttribute(key: String, value: Boolean): SpanWrapper
    fun <T : Any> setAttribute(key: AttributeKeyWrapper<T>, value: T): SpanWrapper
    fun setAttribute(key: AttributeKeyWrapper<Long>, value: Int): SpanWrapper
    fun setAllAttributes(attributes: AttributesWrapper): SpanWrapper
    fun addEvent(name: String): SpanWrapper
    fun addEvent(name: String, timestamp: Long, unit: DurationUnit): SpanWrapper
    fun addEvent(name: String, attributes: AttributesWrapper): SpanWrapper
    fun addEvent(name: String, attributes: AttributesWrapper, timestamp: Long, unit: DurationUnit): SpanWrapper
    fun setStatus(statusCode: StatusCodeWrapper): SpanWrapper
    fun setStatus(statusCode: StatusCodeWrapper, description: String): SpanWrapper
    fun recordException(exception: Throwable): SpanWrapper
    fun recordException(exception: Throwable, additionalAttributes: AttributesWrapper): SpanWrapper
    fun updateName(name: String): SpanWrapper
    fun end()
    fun end(timestamp: Long, unit: DurationUnit)
    val spanContext: SpanContextWrapper
    val isRecording: Boolean
}
