package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.DoubleCounterBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class DoubleCounterBuilderWrapper(
    override val wrappedObject: DoubleCounterBuilder,
) : WrapperBase<DoubleCounterBuilder>(),
        ICounterBuilderWrapper<DoubleCounterWrapper, ObservableDoubleCounterWrapper, ObservableDoubleMeasurementWrapper> {

    override var description: String by cachedWriteOnly("", wrappedObject::setDescription)

    override var unit: String by cachedWriteOnly("", wrappedObject::setUnit)

    override fun build(): DoubleCounterWrapper {
        return wrappedObject.build().letWrapImmutable(::DoubleCounterWrapper)
    }

    override fun buildWithCallback(callback: (ObservableDoubleMeasurementWrapper) -> Unit): ObservableDoubleCounterWrapper {
        val callbackWrapperStore = ObservableDoubleMeasurementWrapper.callbackWrapperStore(callback)

        return wrappedObject
            .buildWithCallback(callbackWrapperStore::acceptCallback)
            .letWrapImmutable(::ObservableDoubleCounterWrapper)
    }

    override fun buildObserver(): ObservableDoubleMeasurementWrapper {
        return wrappedObject.buildObserver().letWrapImmutable(::ObservableDoubleMeasurementWrapper)
    }
}
