package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableLongCounter
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ObservableLongCounterWrapper(
    override val wrappedObject: ObservableLongCounter
) : WrapperBase<ObservableLongCounter>(), ICloseableWrapper {

    override fun close() {
        wrappedObject.close()
    }
}
