package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.DoubleGaugeBuilder
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class DoubleGaugeBuilderWrapper(
    override val wrappedObject: DoubleGaugeBuilder,
) :
        WrapperBase<DoubleGaugeBuilder>(),
        IGaugeBuilderWrapper<ObservableDoubleGaugeWrapper, ObservableDoubleMeasurementWrapper> {

    override var description: String by cachedWriteOnly("",wrappedObject::setDescription)
    override var unit: String by cachedWriteOnly("",wrappedObject::setUnit)

    override fun buildWithCallback(callbackWrapper: (ObservableDoubleMeasurementWrapper) -> Unit): ObservableDoubleGaugeWrapper {
        val callbackWrapperStore = ObservableDoubleMeasurementWrapper.callbackWrapperStore(callbackWrapper)
        return wrappedObject
            .buildWithCallback(callbackWrapperStore::acceptCallback)
            .letWrapImmutable(::ObservableDoubleGaugeWrapper)
    }

    override fun buildObserver(): ObservableDoubleMeasurementWrapper {
        return wrappedObject.buildObserver().letWrapImmutable(::ObservableDoubleMeasurementWrapper)
    }
}
