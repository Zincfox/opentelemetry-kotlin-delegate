package io.opentelemetry.kotlindelegate.api.trace

actual val NoopTracerProvider: TracerProvider
    get() = TracerProvider.noop()
