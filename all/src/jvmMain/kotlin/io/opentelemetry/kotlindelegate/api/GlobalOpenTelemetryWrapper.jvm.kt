package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.api.metrics.MeterProviderWrapper
import io.opentelemetry.kotlindelegate.api.trace.TracerProviderWrapper
import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagatorsWrapper
import io.opentelemetry.kotlindelegate.utils.IOpenTelemetry

actual object GlobalOpenTelemetryWrapper : IOpenTelemetry {

    actual fun get(): OpenTelemetryWrapper {
        return GlobalOpenTelemetry.get().let(::OpenTelemetryWrapper)
    }

    actual fun set(openTelemetry: OpenTelemetryWrapper) {
        GlobalOpenTelemetry.set(openTelemetry.wrappedObject)
    }

    override val tracerProvider: TracerProviderWrapper
        get() = GlobalOpenTelemetry.getTracerProvider().let(::TracerProviderWrapper)
    override val meterProvider: MeterProviderWrapper
        get() = GlobalOpenTelemetry.getMeterProvider().let(::MeterProviderWrapper)
    override val propagators: ContextPropagatorsWrapper
        get() = GlobalOpenTelemetry.getPropagators().let(::ContextPropagatorsWrapper)
}
