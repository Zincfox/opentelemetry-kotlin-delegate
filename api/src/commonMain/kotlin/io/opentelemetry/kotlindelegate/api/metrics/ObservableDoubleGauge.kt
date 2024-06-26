package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableDoubleGauge : io.opentelemetry.kotlindelegate.utils.java.AutoCloseable {
    open override fun close()
}
