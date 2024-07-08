package io.opentelemetry.kotlindelegate.api.metrics

actual interface ObservableDoubleUpDownCounter : AutoCloseable {

    actual override fun close() {
    }
}
