package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

actual class DoubleHistogramWrapper : IWrapper {

    actual fun record(value: Double) {
    }

    actual fun record(
        value: Double,
        attributesWrapper: AttributesWrapper,
    ) {
    }

    actual fun record(
        value: Double,
        attributesWrapper: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
    }
}
