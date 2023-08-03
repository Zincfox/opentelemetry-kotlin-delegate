package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.AttributeKeyWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyedWrapper
import kotlin.time.DurationUnit

actual class SpanWrapper : ImplicitContextKeyedWrapper() {
    actual companion object {

        actual fun current(): SpanWrapper {
            TODO("Not yet implemented")
        }

        actual fun fromContext(context: ContextWrapper): SpanWrapper {
            TODO("Not yet implemented")
        }

        actual fun fromContextOrNull(context: ContextWrapper): SpanWrapper? {
            TODO("Not yet implemented")
        }

        actual val invalid: SpanWrapper
            get() = TODO("Not yet implemented")

        actual fun wrap(spanContext: SpanContextWrapper): SpanWrapper {
            TODO("Not yet implemented")
        }
    }

    actual fun setAttribute(
        key: String,
        value: String,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: String,
        value: Long,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: String,
        value: Double,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: String,
        value: Boolean,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun <T : Any> setAttribute(
        key: AttributeKeyWrapper<T>,
        value: T,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: AttributeKeyWrapper<Long>,
        value: Int,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAllAttributes(attributes: AttributesWrapper): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun addEvent(name: String): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun addEvent(
        name: String,
        timestamp: Long,
        unit: DurationUnit,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun addEvent(
        name: String,
        attributes: AttributesWrapper,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun addEvent(
        name: String,
        attributes: AttributesWrapper,
        timestamp: Long,
        unit: DurationUnit,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun setStatus(
        statusCode: StatusCodeWrapper,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun setStatus(
        statusCode: StatusCodeWrapper,
        description: String,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun recordException(exception: Throwable): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun recordException(
        exception: Throwable,
        additionalAttributes: AttributesWrapper,
    ): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun updateName(name: String): SpanWrapper {
        TODO("Not yet implemented")
    }

    actual fun end() {
        TODO("Not yet implemented")
    }

    actual fun end(timestamp: Long, unit: DurationUnit) {
    }

    actual val spanContext: SpanContextWrapper
        get() = TODO("Not yet implemented")
    actual val isRecording: Boolean
        get() = TODO("Not yet implemented")
}
