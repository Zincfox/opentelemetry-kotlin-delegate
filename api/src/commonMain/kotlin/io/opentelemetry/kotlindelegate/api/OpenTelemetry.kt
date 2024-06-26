package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.kotlindelegate.api.metrics.Meter
import io.opentelemetry.kotlindelegate.api.metrics.MeterBuilder
import io.opentelemetry.kotlindelegate.api.metrics.MeterProvider
import io.opentelemetry.kotlindelegate.api.trace.Tracer
import io.opentelemetry.kotlindelegate.api.trace.TracerBuilder
import io.opentelemetry.kotlindelegate.api.trace.TracerProvider
import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagators

expect interface OpenTelemetry {

    fun getTracerProvider(): TracerProvider
    open fun getTracer(instrumentationScopeName: String): Tracer
    open fun tracerBuilder(instrumentationScopeName: String): TracerBuilder

    open fun getMeterProvider(): MeterProvider
    open fun getMeter(instrumentationScopeName: String): Meter
    open fun meterBuilder(instrumentationScopeName: String): MeterBuilder

    fun getPropagators(): ContextPropagators
}
