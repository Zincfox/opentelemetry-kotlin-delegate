package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.LongCounterBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class LongCounterBuilderWrapper(
    override val wrappedObject: LongCounterBuilder,
) : WrapperBase<LongCounterBuilder>(),
        ICounterBuilderWrapper<LongCounterWrapper, ObservableLongCounterWrapper, ObservableLongMeasurementWrapper> {

    override var description: String by cachedWriteOnly("", wrappedObject::setDescription)

    override var unit: String by cachedWriteOnly("", wrappedObject::setUnit)

    override fun build(): LongCounterWrapper {
        return wrappedObject.build().letWrapImmutable(::LongCounterWrapper)
    }

    override fun buildWithCallback(callback: (ObservableLongMeasurementWrapper) -> Unit): ObservableLongCounterWrapper {
        val callbackWrapperStore = ObservableLongMeasurementWrapper.callbackWrapperStore(callback)

        return wrappedObject
            .buildWithCallback(callbackWrapperStore::acceptCallback)
            .letWrapImmutable(::ObservableLongCounterWrapper)
    }

    override fun buildObserver(): ObservableLongMeasurementWrapper {
        return wrappedObject.buildObserver().letWrapImmutable(::ObservableLongMeasurementWrapper)
    }
}
