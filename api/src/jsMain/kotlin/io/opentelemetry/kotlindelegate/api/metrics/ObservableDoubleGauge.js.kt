package io.opentelemetry.kotlindelegate.api.metrics

actual interface ObservableDoubleGauge : AutoCloseable {

    actual override fun close() {
    }
}
