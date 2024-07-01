package io.opentelemetry.kotlindelegate.api.metrics

actual interface ObservableLongGauge : AutoCloseable {

    actual override fun close() {
    }
}
