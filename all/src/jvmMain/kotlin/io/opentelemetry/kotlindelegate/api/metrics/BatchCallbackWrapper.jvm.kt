package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.BatchCallback
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper

actual class BatchCallbackWrapper(
    override val wrappedObject: BatchCallback
) : WrapperBase<BatchCallback>(), ICloseableWrapper{

    override fun close() {
        wrappedObject.close()
    }
}
