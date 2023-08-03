package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableDoubleCounter
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableDoubleCounterWrapper(
    override val wrappedObject: ObservableDoubleCounter
) : WrapperBase<ObservableDoubleCounter>(), ICloseableWrapper {

    override fun close() {
        wrappedObject.close()
    }
}
