package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.*
import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.HrTime
import io.opentelemetry.kotlindelegate.js.SpanOptions
import io.opentelemetry.kotlindelegate.js.jsPairOf
import io.opentelemetry.kotlindelegate.js.Tracer as JsTracer
import io.opentelemetry.kotlindelegate.js.Link as JsLink
import io.opentelemetry.kotlindelegate.js.SpanKind as JsSpanKind
import io.opentelemetry.kotlindelegate.js.Context as JsContext
import io.opentelemetry.kotlindelegate.js.context as JsContextAPI

actual interface SpanBuilder {

    actual fun setParent(context: Context): SpanBuilder
    actual fun setNoParent(): SpanBuilder
    actual fun addLink(spanContext: SpanContext): SpanBuilder
    actual fun addLink(
        spanContext: SpanContext,
        attributes: Attributes,
    ): SpanBuilder

    actual fun setAttribute(
        key: String,
        value: String,
    ): SpanBuilder

    actual fun setAttribute(
        key: String,
        value: Long,
    ): SpanBuilder

    actual fun setAttribute(
        key: String,
        value: Double,
    ): SpanBuilder

    actual fun setAttribute(
        key: String,
        value: Boolean,
    ): SpanBuilder

    actual fun <T> setAttribute(
        key: AttributeKey<T>,
        value: T,
    ): SpanBuilder

    actual fun setAllAttributes(attributes: Attributes): SpanBuilder {
        attributes.forEach { key, value -> setAttribute(key.unsafeCast<AttributeKey<Any>>(), value) }
        return this
    }

    actual fun setSpanKind(spanKind: SpanKind): SpanBuilder
    actual fun startSpan(): Span
}

interface TimedSpanBuilder : SpanBuilder {

    fun setStartTimestampMillis(startTimestampMillis: Long): SpanBuilder
    fun setStartTimestampNanos(startTimestampNanos: Long): SpanBuilder
}

internal class SpanBuilderImpl(val name: String, val tracer: JsTracer) : TimedSpanBuilder {

    var spanKind: JsSpanKind? = null
    var parent: JsContext? = null
    var autoParent: Boolean = true
    var links: MutableList<JsLink>? = null
    var attributesBuilder: AttributesBuilder? = null
    var startTimeHr: HrTime? = null

    override fun setStartTimestampMillis(startTimestampMillis: Long): SpanBuilder {
        this.startTimeHr = millisToHrTime(startTimestampMillis)
        return this
    }

    override fun setStartTimestampNanos(startTimestampNanos: Long): SpanBuilder {
        this.startTimeHr = nanosToHrTime(startTimestampNanos)
        return this
    }

    override fun setParent(context: Context): SpanBuilder {
        parent = context.asJsContext()
        autoParent = false
        return this
    }

    override fun setNoParent(): SpanBuilder {
        parent = null
        autoParent = false
        return this
    }

    override fun addLink(spanContext: SpanContext): SpanBuilder {
        if (links == null) {
            links = mutableListOf()
        }
        links!!.add(js("{}").unsafeCast<JsLink>().also {
            it.context = spanContext.asJsSpanContext()
        })
        return this
    }

    override fun addLink(spanContext: SpanContext, attributes: Attributes): SpanBuilder {
        if (links == null) {
            links = mutableListOf()
        }
        links!!.add(js("{}").unsafeCast<JsLink>().also {
            it.context = spanContext.asJsSpanContext()
            it.attributes = attributes.asJsAttributes()
        })
        return this
    }

    override fun setAttribute(key: String, value: String): SpanBuilder {
        if (attributesBuilder == null) {
            attributesBuilder = AttributesStatic.builder()
        }
        attributesBuilder!!.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Long): SpanBuilder {
        if (attributesBuilder == null) {
            attributesBuilder = AttributesStatic.builder()
        }
        attributesBuilder!!.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Double): SpanBuilder {
        if (attributesBuilder == null) {
            attributesBuilder = AttributesStatic.builder()
        }
        attributesBuilder!!.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Boolean): SpanBuilder {
        if (attributesBuilder == null) {
            attributesBuilder = AttributesStatic.builder()
        }
        attributesBuilder!!.put(key, value)
        return this
    }

    override fun <T> setAttribute(key: AttributeKey<T>, value: T): SpanBuilder {
        if (attributesBuilder == null) {
            attributesBuilder = AttributesStatic.builder()
        }
        attributesBuilder!!.put(key, value)
        return this
    }

    override fun setSpanKind(spanKind: SpanKind): SpanBuilder {
        this.spanKind = spanKind.jsKind
        return this
    }

    override fun startSpan(): Span {
        val options: SpanOptions = js("{}").unsafeCast<SpanOptions>()
        val attributesBuilder = attributesBuilder
        if (attributesBuilder != null) {
            options.attributes = attributesBuilder.build().asJsAttributes()
        }
        val links: List<JsLink>? = links
        if (links != null) {
            options.links = links.toTypedArray()
        }
        val parent: JsContext? = if (autoParent)
            JsContextAPI.active()
        else {
            if (this.parent == null) {
                options.root = true
            }
            this.parent
        }
        val spanKind = spanKind
        if (spanKind != null) {
            options.kind = spanKind
        }
        val startTimeHr = startTimeHr
        if (startTimeHr != null) {
            options.startTime = startTimeHr
        }
        val span = if (parent != null) {
            tracer.startSpan(name, options, parent)
        } else {
            tracer.startSpan(name, options)
        }
        return SpanWrapper(span)
    }
}

fun nanosToHrTime(nanos: Long): HrTime {
    return jsPairOf(nanos / 1_000_000_000, nanos % 1_000_000_000)
}

fun millisToHrTime(millis: Long): HrTime {
    val seconds = millis / 1000
    return jsPairOf(seconds, (millis - seconds * 1000) * 1_000_000)
}

actual fun SpanBuilder.setStartTimestampMillis(startTimestampMillis: Long): SpanBuilder {
    return (this as TimedSpanBuilder).setStartTimestampMillis(startTimestampMillis)
}

actual fun SpanBuilder.setStartTimestampNanos(startTimestampNanos: Long): SpanBuilder {
    return (this as TimedSpanBuilder).setStartTimestampNanos(startTimestampNanos)
}
