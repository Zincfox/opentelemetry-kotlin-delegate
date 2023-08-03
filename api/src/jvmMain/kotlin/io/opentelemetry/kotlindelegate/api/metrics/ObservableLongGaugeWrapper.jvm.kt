package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableLongGauge
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableLongGaugeWrapper(
    override val wrappedObject: ObservableLongGauge
) : WrapperBase<ObservableLongGauge>(), ICloseableWrapper {

    override fun close() {
        wrappedObject.close()
    }
}
