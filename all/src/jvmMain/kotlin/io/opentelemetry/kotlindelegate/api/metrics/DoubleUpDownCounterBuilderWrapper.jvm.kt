package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.DoubleUpDownCounterBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class DoubleUpDownCounterBuilderWrapper(
    override val wrappedObject: DoubleUpDownCounterBuilder,
) : WrapperBase<DoubleUpDownCounterBuilder>(),
        ICounterBuilderWrapper<DoubleUpDownCounterWrapper, ObservableDoubleUpDownCounterWrapper, ObservableDoubleMeasurementWrapper> {

    override var description: String by cachedWriteOnly("", wrappedObject::setDescription)

    override var unit: String by cachedWriteOnly("", wrappedObject::setUnit)

    override fun build(): DoubleUpDownCounterWrapper {
        return wrappedObject.build().letWrapImmutable(::DoubleUpDownCounterWrapper)
    }

    override fun buildWithCallback(callback: (ObservableDoubleMeasurementWrapper) -> Unit): ObservableDoubleUpDownCounterWrapper {
        val callbackWrapperStore = ObservableDoubleMeasurementWrapper.callbackWrapperStore(callback)

        return wrappedObject
            .buildWithCallback(callbackWrapperStore::acceptCallback)
            .letWrapImmutable(::ObservableDoubleUpDownCounterWrapper)
    }

    override fun buildObserver(): ObservableDoubleMeasurementWrapper {
        return wrappedObject.buildObserver().letWrapImmutable(::ObservableDoubleMeasurementWrapper)
    }
}
