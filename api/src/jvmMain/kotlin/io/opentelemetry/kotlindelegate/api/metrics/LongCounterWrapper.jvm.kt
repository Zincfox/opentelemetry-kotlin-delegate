package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.LongCounter
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class LongCounterWrapper(
    override val wrappedObject: LongCounter,
) : WrapperBase<LongCounter>(), IWrapper {

    actual fun add(value: Long) {
        wrappedObject.add(value)
    }

    actual fun add(value: Long, attributes: AttributesWrapper) {
        wrappedObject.add(value, attributes.wrappedObject)
    }

    actual fun add(
        value: Long,
        attributes: AttributesWrapper,
        context: ContextWrapper,
    ) {
        wrappedObject.add(value, attributes.wrappedObject, context.wrappedObject)
    }
}
