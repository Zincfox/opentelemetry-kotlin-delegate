package io.opentelemetry.kotlindelegate.api.metrics

actual val NoopMeterProvider: MeterProvider
    get() = io.opentelemetry.api.metrics.MeterProvider.noop()
