package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableLongUpDownCounter
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableLongUpDownCounterWrapper(
    override val wrappedObject: ObservableLongUpDownCounter
) : WrapperBase<ObservableLongUpDownCounter>(), ICloseableWrapper {

    override fun close() {
        wrappedObject.close()
    }
}
