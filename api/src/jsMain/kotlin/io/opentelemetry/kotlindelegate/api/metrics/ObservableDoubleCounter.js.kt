package io.opentelemetry.kotlindelegate.api.metrics

actual interface ObservableDoubleCounter : AutoCloseable {

    actual override fun close() {
    }
}
