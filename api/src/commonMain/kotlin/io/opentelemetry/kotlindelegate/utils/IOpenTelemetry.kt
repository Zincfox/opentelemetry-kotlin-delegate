package io.opentelemetry.kotlindelegate.utils

import io.opentelemetry.kotlindelegate.api.metrics.MeterBuilderWrapper
import io.opentelemetry.kotlindelegate.api.metrics.MeterProviderWrapper
import io.opentelemetry.kotlindelegate.api.metrics.MeterWrapper
import io.opentelemetry.kotlindelegate.api.metrics.buildMeter
import io.opentelemetry.kotlindelegate.api.trace.TracerBuilderWrapper
import io.opentelemetry.kotlindelegate.api.trace.TracerProviderWrapper
import io.opentelemetry.kotlindelegate.api.trace.TracerWrapper
import io.opentelemetry.kotlindelegate.api.trace.buildTracer
import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagatorsWrapper

interface IOpenTelemetry {

    val tracerProvider: TracerProviderWrapper
    val meterProvider: MeterProviderWrapper

    //val logsBridge: LoggerProviderWrapper
    val propagators: ContextPropagatorsWrapper

    fun getTracer(
        instrumentationScopeName: String,
    ): TracerWrapper = tracerProvider.get(
        instrumentationScopeName,
    )

    fun getTracer(
        instrumentationScopeName: String,
        instrumentationScopeVersion: String,
    ): TracerWrapper = tracerProvider.get(
        instrumentationScopeName,
        instrumentationScopeVersion,
    )

    fun tracerBuilder(
        instrumentationScopeName: String,
    ): TracerBuilderWrapper = tracerProvider.tracerBuilder(instrumentationScopeName)

    fun buildTracer(
        instrumentationScopeName: String,
        block: TracerBuilderWrapper.() -> Unit,
    ): TracerWrapper = tracerProvider.buildTracer(instrumentationScopeName, block)

    fun getMeter(
        instrumentationScopeName: String,
    ): MeterWrapper = meterProvider.get(
        instrumentationScopeName,
    )

    fun meterBuilder(
        instrumentationScopeName: String,
    ): MeterBuilderWrapper = meterProvider.meterBuilder(instrumentationScopeName)

    fun buildMeter(
        instrumentationScopeName: String,
        block: MeterBuilderWrapper.() -> Unit,
    ): MeterWrapper = meterProvider.buildMeter(instrumentationScopeName, block)
}
