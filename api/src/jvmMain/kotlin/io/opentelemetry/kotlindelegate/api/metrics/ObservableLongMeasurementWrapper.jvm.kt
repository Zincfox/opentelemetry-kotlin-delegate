package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableLongMeasurement
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableLongMeasurementWrapper(
    override val wrappedObject: ObservableLongMeasurement
) : WrapperBase<ObservableLongMeasurement>(), ObservableMeasurementWrapper {

    actual fun record(value: Long) {
        wrappedObject.record(value)
    }

    actual fun record(value: Long, attributes: AttributesWrapper) {
        wrappedObject.record(value, attributes.wrappedObject)
    }

    companion object {

        internal fun callbackWrapperStore(
            callback: (ObservableLongMeasurementWrapper) -> Unit,
        ): ObservableCallbackWrapperStore<ObservableLongMeasurement, ObservableLongMeasurementWrapper> {
            return ObservableCallbackWrapperStore(::ObservableLongMeasurementWrapper,callback)
        }
    }
}
