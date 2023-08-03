package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.kotlindelegate.api.metrics.MeterProviderWrapper
import io.opentelemetry.kotlindelegate.api.trace.TracerProviderWrapper
import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagatorsWrapper
import io.opentelemetry.kotlindelegate.utils.IOpenTelemetry

actual object GlobalOpenTelemetryWrapper : IOpenTelemetry {

    actual fun get(): OpenTelemetryWrapper {
        TODO("Not yet implemented")
    }

    actual fun set(openTelemetry: OpenTelemetryWrapper) {
        TODO()
    }

    override val tracerProvider: TracerProviderWrapper
        get() = TODO("Not yet implemented")

    override val meterProvider: MeterProviderWrapper
        get() = TODO("Not yet implemented")

    override val propagators: ContextPropagatorsWrapper
        get() = TODO("Not yet implemented")
}
