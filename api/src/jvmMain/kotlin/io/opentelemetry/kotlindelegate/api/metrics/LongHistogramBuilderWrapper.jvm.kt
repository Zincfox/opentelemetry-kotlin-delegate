package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.LongHistogramBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class LongHistogramBuilderWrapper(
    override val wrappedObject: LongHistogramBuilder,
) : WrapperBase<LongHistogramBuilder>(), IHistogramBuilderWrapper<LongHistogramWrapper> {

    override var description: String by cachedWriteOnly("", wrappedObject::setDescription)

    override var unit: String by cachedWriteOnly("", wrappedObject::setUnit)

    override fun build(): LongHistogramWrapper {
        return wrappedObject.build().letWrapImmutable(::LongHistogramWrapper)
    }
}
