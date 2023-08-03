package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.LongGaugeBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class LongGaugeBuilderWrapper(
    override val wrappedObject: LongGaugeBuilder,
) :
        WrapperBase<LongGaugeBuilder>(),
        IGaugeBuilderWrapper<ObservableLongGaugeWrapper, ObservableLongMeasurementWrapper> {

    override var description: String by cachedWriteOnly("",wrappedObject::setDescription)
    override var unit: String by cachedWriteOnly("",wrappedObject::setUnit)

    override fun buildWithCallback(callbackWrapper: (ObservableLongMeasurementWrapper) -> Unit): ObservableLongGaugeWrapper {
        val callbackWrapperStore = ObservableLongMeasurementWrapper.callbackWrapperStore(callbackWrapper)
        return wrappedObject
            .buildWithCallback(callbackWrapperStore::acceptCallback)
            .letWrapImmutable(::ObservableLongGaugeWrapper)
    }

    override fun buildObserver(): ObservableLongMeasurementWrapper {
        return wrappedObject.buildObserver().letWrapImmutable(::ObservableLongMeasurementWrapper)
    }
}
