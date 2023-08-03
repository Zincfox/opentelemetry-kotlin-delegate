package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.DoubleCounter
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class DoubleCounterWrapper(
    override val wrappedObject: DoubleCounter,
) : WrapperBase<DoubleCounter>(), IWrapper {

    actual fun add(value: Double) {
        wrappedObject.add(value)
    }

    actual fun add(value: Double, attributes: AttributesWrapper) {
        wrappedObject.add(value, attributes.wrappedObject)
    }

    actual fun add(
        value: Double,
        attributes: AttributesWrapper,
        context: ContextWrapper,
    ) {
        wrappedObject.add(value, attributes.wrappedObject, context.wrappedObject)
    }
}
