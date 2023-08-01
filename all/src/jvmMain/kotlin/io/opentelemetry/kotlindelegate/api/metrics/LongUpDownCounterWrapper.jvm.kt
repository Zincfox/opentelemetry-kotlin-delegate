package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.LongUpDownCounter
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class LongUpDownCounterWrapper(
    override val wrappedObject: LongUpDownCounter,
) : WrapperBase<LongUpDownCounter>(), IWrapper {

    actual fun add(value: Long) {
        wrappedObject.add(value)
    }

    actual fun add(value: Long, attributes: AttributesWrapper) {
        wrappedObject.add(value, attributes.wrappedObject)
    }

    actual fun add(
        value: Long,
        attributes: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
        wrappedObject.add(value, attributes.wrappedObject, contextWrapper.wrappedObject)
    }
}
