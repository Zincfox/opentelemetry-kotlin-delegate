package io.opentelemetry.kotlindelegate.api.metrics

actual interface ObservableLongCounter : AutoCloseable {

    actual override fun close() {
    }
}
