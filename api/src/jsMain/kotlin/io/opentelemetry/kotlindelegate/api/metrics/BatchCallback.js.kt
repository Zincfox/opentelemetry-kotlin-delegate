package io.opentelemetry.kotlindelegate.api.metrics

actual interface BatchCallback : AutoCloseable {

    actual override fun close() {}
}

internal class BatchCallbackImpl(private val closer: () -> Unit) : BatchCallback {
    override fun close() {
        closer()
    }
}
