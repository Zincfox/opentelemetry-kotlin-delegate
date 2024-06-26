package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableLongGauge : io.opentelemetry.kotlindelegate.utils.java.AutoCloseable {
    open override fun close()
}
