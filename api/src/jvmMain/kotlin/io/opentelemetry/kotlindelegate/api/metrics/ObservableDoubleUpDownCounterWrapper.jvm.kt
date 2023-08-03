package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableDoubleUpDownCounter
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableDoubleUpDownCounterWrapper(
    override val wrappedObject: ObservableDoubleUpDownCounter
) : WrapperBase<ObservableDoubleUpDownCounter>(), ICloseableWrapper {

    override fun close() {
        wrappedObject.close()
    }
}
