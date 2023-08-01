package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

actual class DoubleUpDownCounterWrapper : IWrapper {

    actual fun add(value: Double) {
    }

    actual fun add(value: Double, attributes: AttributesWrapper) {
    }

    actual fun add(
        value: Double,
        attributes: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
    }
}
