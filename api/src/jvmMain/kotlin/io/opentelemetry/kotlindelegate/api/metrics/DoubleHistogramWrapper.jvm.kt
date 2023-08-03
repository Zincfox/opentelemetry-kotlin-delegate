package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.DoubleHistogram
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class DoubleHistogramWrapper(
    override val wrappedObject: DoubleHistogram,
) : WrapperBase<DoubleHistogram>(), IWrapper {

    actual fun record(value: Double) {
        wrappedObject.record(value)
    }

    actual fun record(
        value: Double,
        attributesWrapper: AttributesWrapper,
    ) {
        wrappedObject.record(value, attributesWrapper.wrappedObject)
    }

    actual fun record(
        value: Double,
        attributesWrapper: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
        wrappedObject.record(value, attributesWrapper.wrappedObject, contextWrapper.wrappedObject)
    }
}
