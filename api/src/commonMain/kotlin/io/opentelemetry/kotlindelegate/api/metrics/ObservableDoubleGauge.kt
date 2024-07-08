package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableDoubleGauge : AutoCloseable {
    open override fun close()
}
