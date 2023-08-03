package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

actual class LongUpDownCounterWrapper : IWrapper {

    actual fun add(value: Long) {
    }

    actual fun add(value: Long, attributes: AttributesWrapper) {
    }

    actual fun add(
        value: Long,
        attributes: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
    }
}
