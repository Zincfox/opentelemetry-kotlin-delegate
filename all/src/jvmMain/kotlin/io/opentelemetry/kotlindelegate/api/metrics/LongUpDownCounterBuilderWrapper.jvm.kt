package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.LongUpDownCounterBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class LongUpDownCounterBuilderWrapper(
    override val wrappedObject: LongUpDownCounterBuilder,
) : WrapperBase<LongUpDownCounterBuilder>(),
        ICounterBuilderWrapper<LongUpDownCounterWrapper, ObservableLongUpDownCounterWrapper, ObservableLongMeasurementWrapper> {

    override var description: String by cachedWriteOnly("", wrappedObject::setDescription)

    override var unit: String by cachedWriteOnly("", wrappedObject::setUnit)

    override fun build(): LongUpDownCounterWrapper {
        return wrappedObject.build().letWrapImmutable(::LongUpDownCounterWrapper)
    }

    override fun buildWithCallback(callback: (ObservableLongMeasurementWrapper) -> Unit): ObservableLongUpDownCounterWrapper {
        val callbackWrapperStore = ObservableLongMeasurementWrapper.callbackWrapperStore(callback)

        return wrappedObject
            .buildWithCallback(callbackWrapperStore::acceptCallback)
            .letWrapImmutable(::ObservableLongUpDownCounterWrapper)
    }

    override fun buildObserver(): ObservableLongMeasurementWrapper {
        return wrappedObject.buildObserver().letWrapImmutable(::ObservableLongMeasurementWrapper)
    }
}
