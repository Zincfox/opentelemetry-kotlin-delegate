package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableDoubleMeasurement
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableDoubleMeasurementWrapper(
    override val wrappedObject: ObservableDoubleMeasurement
) : WrapperBase<ObservableDoubleMeasurement>(), ObservableMeasurementWrapper {

    actual fun record(value: Double) {
        wrappedObject.record(value)
    }

    actual fun record(value: Double, attributes: AttributesWrapper) {
        wrappedObject.record(value, attributes.wrappedObject)
    }

    companion object {

        internal fun callbackWrapperStore(
            callback: (ObservableDoubleMeasurementWrapper) -> Unit,
        ): ObservableCallbackWrapperStore<ObservableDoubleMeasurement, ObservableDoubleMeasurementWrapper> {
            return ObservableCallbackWrapperStore(::ObservableDoubleMeasurementWrapper,callback)
        }
    }
}
