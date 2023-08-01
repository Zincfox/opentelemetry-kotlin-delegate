package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.AttributeKeyWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import kotlin.time.DurationUnit

actual class SpanBuilderWrapper : IWrapper {

    actual fun setParent(context: ContextWrapper): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setNoParent(): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun addLink(spanContext: SpanContextWrapper): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun addLink(
        spanContext: SpanContextWrapper,
        attributes: AttributesWrapper,
    ): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: String,
        value: String,
    ): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: String,
        value: Long,
    ): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: String,
        value: Double,
    ): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAttribute(
        key: String,
        value: Boolean,
    ): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun <T:Any> setAttribute(
        key: AttributeKeyWrapper<T>,
        value: T,
    ): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setAllAttributes(attributes: AttributesWrapper): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setSpanKind(spanKind: SpanKindWrapper): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun setStartTimestamp(
        startTimestamp: Long,
        unit: DurationUnit,
    ): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun startSpan(): SpanWrapper {
        TODO("Not yet implemented")
    }
}
