package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableDoubleGauge
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableDoubleGaugeWrapper(
    override val wrappedObject: ObservableDoubleGauge
) : WrapperBase<ObservableDoubleGauge>(), ICloseableWrapper {

    override fun close() {
        wrappedObject.close()
    }
}
