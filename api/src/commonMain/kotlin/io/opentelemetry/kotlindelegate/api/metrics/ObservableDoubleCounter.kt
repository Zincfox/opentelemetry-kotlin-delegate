package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableDoubleCounter : AutoCloseable {
    open override fun close()
}
