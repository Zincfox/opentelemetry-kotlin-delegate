package io.opentelemetry.kotlindelegate.api.metrics

actual interface ObservableLongUpDownCounter : AutoCloseable {

    actual override fun close() {
    }
}
