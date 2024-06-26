package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableLongGauge : AutoCloseable {
    open override fun close()
}
