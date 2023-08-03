package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.Span
import io.opentelemetry.kotlindelegate.api.common.AttributeKeyWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyedWrapper
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable
import kotlin.time.DurationUnit
import kotlin.time.toTimeUnit

actual class SpanWrapper(
    override val wrappedObject: Span
) : ImplicitContextKeyedWrapper(wrappedObject) {
    actual companion object {

        actual fun current(): SpanWrapper {
            return Span.current().let(::SpanWrapper)
        }

        actual fun fromContext(context: ContextWrapper): SpanWrapper {
            return Span.fromContext(context.wrappedObject).let(::SpanWrapper)
        }

        actual fun fromContextOrNull(context: ContextWrapper): SpanWrapper? {
            return Span.fromContextOrNull(context.wrappedObject)?.let(::SpanWrapper)
        }

        actual val invalid: SpanWrapper by lazy {
            Span.getInvalid().let(::SpanWrapper)
        }

        actual fun wrap(spanContext: SpanContextWrapper): SpanWrapper {
            return Span.wrap(spanContext.wrappedObject).let(::SpanWrapper)
        }
    }

    actual fun setAttribute(
        key: String,
        value: String,
    ): SpanWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun setAttribute(
        key: String,
        value: Long,
    ): SpanWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun setAttribute(
        key: String,
        value: Double,
    ): SpanWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun setAttribute(
        key: String,
        value: Boolean,
    ): SpanWrapper {
        wrappedObject.setAttribute(key, value)
        return this
    }

    actual fun <T:Any> setAttribute(
        key: AttributeKeyWrapper<T>,
        value: T,
    ): SpanWrapper {
        wrappedObject.setAttribute(key.wrappedObject, value)
        return this
    }

    actual fun setAttribute(
        key: AttributeKeyWrapper<Long>,
        value: Int,
    ): SpanWrapper {
        wrappedObject.setAttribute(key.wrappedObject, value)
        return this
    }

    actual fun setAllAttributes(attributes: AttributesWrapper): SpanWrapper {
        wrappedObject.setAllAttributes(attributes.wrappedObject)
        return this
    }

    actual fun addEvent(name: String): SpanWrapper {
        wrappedObject.addEvent(name)
        return this
    }

    actual fun addEvent(
        name: String,
        timestamp: Long,
        unit: DurationUnit,
    ): SpanWrapper {
        wrappedObject.addEvent(name, timestamp,unit.toTimeUnit())
        return this
    }

    actual fun addEvent(
        name: String,
        attributes: AttributesWrapper,
    ): SpanWrapper {
        wrappedObject.addEvent(name, attributes.wrappedObject)
        return this
    }

    actual fun addEvent(
        name: String,
        attributes: AttributesWrapper,
        timestamp: Long,
        unit: DurationUnit,
    ): SpanWrapper {
        wrappedObject.addEvent(name, attributes.wrappedObject, timestamp, unit.toTimeUnit())
        return this
    }

    actual fun setStatus(
        statusCode: StatusCodeWrapper,
    ): SpanWrapper {
        wrappedObject.setStatus(statusCode.wrappedObject)
        return this
    }
    actual fun setStatus(
        statusCode: StatusCodeWrapper,
        description: String,
    ): SpanWrapper {
        wrappedObject.setStatus(statusCode.wrappedObject, description)
        return this
    }

    actual fun recordException(exception: Throwable): SpanWrapper {
        wrappedObject.recordException(exception)
        return this
    }

    actual fun recordException(
        exception: Throwable,
        additionalAttributes: AttributesWrapper,
    ): SpanWrapper {
        wrappedObject.recordException(exception, additionalAttributes.wrappedObject)
        return this
    }

    actual fun updateName(name: String): SpanWrapper {
        wrappedObject.updateName(name)
        return this
    }

    actual fun end() {
        wrappedObject.end()
    }

    actual fun end(timestamp: Long, unit: DurationUnit) {
        wrappedObject.end(timestamp, unit.toTimeUnit())
    }

    actual val spanContext: SpanContextWrapper
        get() = wrappedObject.spanContext.letWrapImmutable(::SpanContextWrapper)
    actual val isRecording: Boolean
        get() = wrappedObject.isRecording
}
