package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.DoubleHistogramBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class DoubleHistogramBuilderWrapper(
    override val wrappedObject: DoubleHistogramBuilder,
) : WrapperBase<DoubleHistogramBuilder>(), IHistogramBuilderWrapper<DoubleHistogramWrapper> {

    override var description: String by cachedWriteOnly("", wrappedObject::setDescription)

    override var unit: String by cachedWriteOnly("", wrappedObject::setUnit)

    override fun build(): DoubleHistogramWrapper {
        return wrappedObject.build().letWrapImmutable(::DoubleHistogramWrapper)
    }
}
