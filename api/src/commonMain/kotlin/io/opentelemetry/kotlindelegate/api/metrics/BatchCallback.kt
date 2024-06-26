package io.opentelemetry.kotlindelegate.api.metrics

expect interface BatchCallback : AutoCloseable {

    open override fun close()
}
