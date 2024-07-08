package io.opentelemetry.kotlindelegate.api.metrics

expect interface ObservableLongCounter : AutoCloseable {
    open override fun close()
}
