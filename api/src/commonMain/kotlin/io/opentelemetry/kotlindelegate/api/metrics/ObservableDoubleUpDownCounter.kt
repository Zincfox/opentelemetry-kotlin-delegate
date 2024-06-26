package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableDoubleUpDownCounter : AutoCloseable {
    open override fun close()
}
