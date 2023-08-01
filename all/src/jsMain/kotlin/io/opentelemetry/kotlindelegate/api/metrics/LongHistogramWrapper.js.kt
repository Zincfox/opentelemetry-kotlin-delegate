package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

actual class LongHistogramWrapper : IWrapper {

    actual fun record(value: Long) {
    }

    actual fun record(
        value: Long,
        attributesWrapper: AttributesWrapper,
    ) {
    }

    actual fun record(
        value: Long,
        attributesWrapper: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
    }
}
