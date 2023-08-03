package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.LongHistogram
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class LongHistogramWrapper(
    override val wrappedObject: LongHistogram,
) : WrapperBase<LongHistogram>(), IWrapper {

    actual fun record(value: Long) {
        wrappedObject.record(value)
    }

    actual fun record(
        value: Long,
        attributesWrapper: AttributesWrapper,
    ) {
        wrappedObject.record(value, attributesWrapper.wrappedObject)
    }

    actual fun record(
        value: Long,
        attributesWrapper: AttributesWrapper,
        contextWrapper: ContextWrapper,
    ) {
        wrappedObject.record(value, attributesWrapper.wrappedObject, contextWrapper.wrappedObject)
    }
}
