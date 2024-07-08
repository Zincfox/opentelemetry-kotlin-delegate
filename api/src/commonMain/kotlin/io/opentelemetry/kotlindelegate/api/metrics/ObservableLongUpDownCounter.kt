package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableLongUpDownCounter : AutoCloseable {
    open override fun close()
}
