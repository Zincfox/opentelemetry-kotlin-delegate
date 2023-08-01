package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.SpanBuilder
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.kotlindelegate.api.common.AttributeKeyWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import kotlin.time.DurationUnit
import kotlin.time.toTimeUnit

actual class SpanBuilderWrapper(
    override val wrappedObject: SpanBuilder,
) : WrapperBase<SpanBuilder>(), IWrapper {

    actual fun setParent(context: ContextWrapper): SpanBuilderWrapper {
        wrappedObject.setParent(context.wrappedObject)
        return this
    }

    actual fun setNoParent(): SpanBuilderWrapper {
        wrappedObject.setNoParent()
        return this
    }

    actual fun addLink(spanContext: SpanContextWrapper): SpanBuilderWrapper {
        wrappedObject.addLink(spanContext.wrappedObject)
        return this
    }

    actual fun addLink(
        spanContext: SpanContextWrapper,
        attributes: AttributesWrapper,
    ): SpanBuilderWrapper {
        wrappedObject.addLink(spanContext.wrappedObject, attributes.wrappedObject)
        return this
    }

    actual fun setAttribute(
        key: String,
        value: String,
    ): SpanBuilderWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun setAttribute(
        key: String,
        value: Long,
    ): SpanBuilderWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun setAttribute(
        key: String,
        value: Double,
    ): SpanBuilderWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun setAttribute(
        key: String,
        value: Boolean,
    ): SpanBuilderWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun <T : Any> setAttribute(
        key: AttributeKeyWrapper<T>,
        value: T,
    ): SpanBuilderWrapper {
        wrappedObject.setAttribute(key.wrappedObject, value)
        return this
    }

    actual fun setAllAttributes(attributes: AttributesWrapper): SpanBuilderWrapper {
        wrappedObject.setAllAttributes(attributes.wrappedObject)
        return this
    }

    actual fun setSpanKind(spanKind: SpanKindWrapper): SpanBuilderWrapper {
        wrappedObject.setSpanKind(spanKind.wrappedObject)
        return this
    }

    actual fun setStartTimestamp(
        startTimestamp: Long,
        unit: DurationUnit,
    ): SpanBuilderWrapper {
        wrappedObject.setStartTimestamp(startTimestamp, unit.toTimeUnit())
        return this
    }

    actual fun startSpan(): SpanWrapper {
        val startedSpan = wrappedObject.startSpan()
        return try {
            startedSpan.let(::SpanWrapper)
        } catch (ex: Throwable) {
            startedSpan.setStatus(StatusCode.ERROR, "Failed to construct span wrapper")
            startedSpan.recordException(ex)
            startedSpan.end()
            throw ex
        }
    }
}
