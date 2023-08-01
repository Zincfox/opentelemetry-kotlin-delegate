package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.DoubleUpDownCounter
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class DoubleUpDownCounterWrapper(
    override val wrappedObject: DoubleUpDownCounter,
) : WrapperBase<DoubleUpDownCounter>(), IWrapper {

    actual fun add(value: Double) {
        wrappedObject.add(value)
    }

    actual fun add(value: Double, attributes: AttributesWrapper) {
        wrappedObject.add(value, attributes.wrappedObject)
    }

    actual fun add(
        value: Double,
        attributes: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
        wrappedObject.add(value, attributes.wrappedObject, contextWrapper.wrappedObject)
    }
}
