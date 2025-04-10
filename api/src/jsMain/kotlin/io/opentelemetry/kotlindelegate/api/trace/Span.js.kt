package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.*
import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.*
import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.*
import io.opentelemetry.kotlindelegate.js.trace
import io.opentelemetry.kotlindelegate.js.Span as JsSpan
import io.opentelemetry.kotlindelegate.js.trace as JsTraceAPI

actual interface Span : ImplicitContextKeyed {

    actual fun setAttribute(key: String, value: String): Span = setAttribute(AttributeKeyStatic.stringKey(key), value)

    actual fun setAttribute(key: String, value: Long): Span = setAttribute(AttributeKeyStatic.longKey(key), value)

    actual fun setAttribute(key: String, value: Double): Span = setAttribute(AttributeKeyStatic.doubleKey(key), value)

    actual fun setAttribute(key: String, value: Boolean): Span = setAttribute(AttributeKeyStatic.booleanKey(key), value)

    actual fun <T> setAttribute(
        key: AttributeKey<T>,
        value: T,
    ): Span

    actual fun setAttribute(
        key: AttributeKey<Long>,
        value: Int,
    ): Span = setAttribute(key, value.toLong())

    actual fun setAllAttributes(attributes: Attributes): Span {
        attributes.forEach { key, value -> setAttribute(key.unsafeCast<AttributeKey<Any>>(), value) }
        return this
    }

    actual fun addEvent(name: String): Span {
        return addEvent(name, AttributesStatic.empty())
    }

    actual fun addEvent(
        name: String,
        attributes: Attributes,
    ): Span

    actual fun setStatus(statusCode: StatusCode): Span {
        return setStatus(statusCode, "")
    }

    actual fun setStatus(
        statusCode: StatusCode,
        description: String,
    ): Span

    actual fun recordException(exception: Throwable): Span = recordException(exception, AttributesStatic.empty())

    actual fun recordException(
        exception: Throwable,
        additionalAttributes: Attributes,
    ): Span

    actual fun updateName(name: String): Span
    actual fun end()
    actual fun getSpanContext(): SpanContext
    actual fun isRecording(): Boolean
    actual override fun storeInContext(context: Context): Context {
        return JsTraceAPI.setSpanContext(context.asJsContext(), getSpanContext().asJsSpanContext()).asCommonContext()
    }
}

interface TimedSpan : Span {

    fun addEventMillis(name: String, timestampMillis: Long): TimedSpan
    fun addEventNanos(name: String, timestampNanos: Long): TimedSpan
    fun addEventMillis(name: String, attributes: Attributes, timestampMillis: Long): TimedSpan
    fun addEventNanos(name: String, attributes: Attributes, timestampNanos: Long): TimedSpan
    fun endMillis(timestampMillis: Long)
    fun endNanos(timestampNanos: Long)
}

actual fun Span.addEventMillis(
    name: String,
    timestampMillis: Long,
): Span = (this as TimedSpan).addEventMillis(name, timestampMillis)

actual fun Span.addEventNanos(
    name: String,
    timestampNanos: Long,
): Span = (this as TimedSpan).addEventNanos(name, timestampNanos)

actual fun Span.addEventMillis(
    name: String,
    attributes: Attributes,
    timestampMillis: Long,
): Span = (this as TimedSpan).addEventMillis(name, attributes, timestampMillis)

actual fun Span.addEventNanos(
    name: String,
    attributes: Attributes,
    timestampNanos: Long,
): Span = (this as TimedSpan).addEventNanos(name, attributes, timestampNanos)

actual fun Span.endMillis(timestampMillis: Long): Unit = (this as TimedSpan).endMillis(timestampMillis)
actual fun Span.endNanos(timestampNanos: Long): Unit = (this as TimedSpan).endNanos(timestampNanos)

internal class SpanWrapper(val span: JsSpan) : TimedSpan {

    override fun addEventMillis(name: String, timestampMillis: Long): TimedSpan {
        span.addEvent(name, millisToHrTime(timestampMillis))
        return this
    }

    override fun addEventMillis(name: String, attributes: Attributes, timestampMillis: Long): TimedSpan {
        span.addEvent(name, attributes.asJsAttributes(), millisToHrTime(timestampMillis))
        return this
    }

    override fun addEventNanos(name: String, timestampNanos: Long): TimedSpan {
        span.addEvent(name, nanosToHrTime(timestampNanos))
        return this
    }

    override fun addEventNanos(name: String, attributes: Attributes, timestampNanos: Long): TimedSpan {
        span.addEvent(name, nanosToHrTime(timestampNanos))
        return this
    }

    override fun endMillis(timestampMillis: Long) {
        span.end(millisToHrTime(timestampMillis))
    }

    override fun endNanos(timestampNanos: Long) {
        span.end(nanosToHrTime(timestampNanos))
    }

    override fun <T> setAttribute(key: AttributeKey<T>, value: T): Span {
        span.setAttribute(key.getKey(), key.getType().forceJsType(value!!))
        return this
    }

    override fun addEvent(name: String, attributes: Attributes): Span {
        span.addEvent(name, attributes.asJsAttributes())
        return this
    }

    override fun setStatus(statusCode: StatusCode, description: String): Span {
        val status = js("{}").unsafeCast<SpanStatus>().also {
            it.code = statusCode.jsStatusCode
            if (description.isNotEmpty())
                it.message = description
        }
        span.setStatus(status)
        return this
    }

    override fun recordException(exception: Throwable, additionalAttributes: Attributes): Span {
        //Cannot record attributes because underlying JS library does not provide this functionality for exceptions.
        span.recordException(exception)
        return this
    }

    override fun recordException(exception: Throwable): Span {
        span.recordException(exception)
        return this
    }

    override fun updateName(name: String): Span {
        span.updateName(name)
        return this
    }

    override fun end() {
        span.end()
    }

    override fun getSpanContext(): SpanContext {
        return SpanContextWrapper(span.spanContext())
    }

    override fun isRecording(): Boolean {
        return span.isRecording()
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as SpanWrapper

        if(span == other.span) return true

        val ownContext = span.spanContext()
        val otherContext = other.span.spanContext()

        if(ownContext == otherContext) return true
        if(ownContext.traceId != otherContext.traceId) return false
        if(ownContext.spanId != otherContext.spanId) return false
        if(ownContext.isRemote != otherContext.isRemote) return false
        if(ownContext.traceFlags != otherContext.traceFlags) return false
        if(ownContext.traceState?.serialize() != otherContext.traceState?.serialize()) return false
        return true
    }

    override fun hashCode(): Int {
        val context = span.spanContext()
        var result = 17
        result = 31 * result + context.traceId.hashCode()
        result = 31 * result + context.spanId.hashCode()
        result = 31 * result + context.isRemote.let { when(it) {
            null -> 0
            false -> 1
            true -> 2
        } }
        result = 31 * result + context.traceFlags
        result = 31 * result + (context.traceState?.serialize()?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "SpanWrapper(span=$span,ctx=${span.spanContext()})"
    }
}

fun JsSpan.asCommonSpan(): TimedSpan = SpanWrapper(this)

actual object SpanStatic {

    actual fun current(): Span {
        return trace.getActiveSpan()?.let { SpanWrapper(it) } ?: getInvalid()
    }

    actual fun fromContext(context: Context): Span {
        return trace.getSpan(context.asJsContext())?.let { SpanWrapper(it) } ?: getInvalid()
    }

    actual fun fromContextOrNull(context: Context): Span? {
        return trace.getSpan(context.asJsContext())?.let { SpanWrapper(it) }
    }

    private val invalidSpanContext: SpanContext = SpanContextWrapper(INVALID_SPAN_CONTEXT)
    private val invalidSpan: Span = wrap(invalidSpanContext)

    actual fun getInvalid(): Span {
        return invalidSpan
    }

    actual fun wrap(spanContext: SpanContext): Span {
        return SpanWrapper(JsTraceAPI.wrapSpanContext(spanContext.asJsSpanContext()))
    }
}
