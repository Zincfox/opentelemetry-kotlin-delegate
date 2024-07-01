package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.kotlindelegate.api.metrics.*
import io.opentelemetry.kotlindelegate.api.trace.Tracer
import io.opentelemetry.kotlindelegate.api.trace.TracerBuilder
import io.opentelemetry.kotlindelegate.api.trace.TracerProvider
import io.opentelemetry.kotlindelegate.api.trace.TracerProviderWrapper
import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagators
import io.opentelemetry.kotlindelegate.js.createNoopMeter
import io.opentelemetry.kotlindelegate.js.MeterOptions as JsMeterOptions
import io.opentelemetry.kotlindelegate.js.Meter as JsMeter
import io.opentelemetry.kotlindelegate.js.MeterProvider as JsMeterProvider
import io.opentelemetry.kotlindelegate.js.context as JsContextAPI
import io.opentelemetry.kotlindelegate.js.trace as JsTraceAPI
import io.opentelemetry.kotlindelegate.js.propagation as JsPropagationAPI
import io.opentelemetry.kotlindelegate.js.metrics as JsMetricsAPI

private val NoopMeterProvider: MeterProvider by lazy {
    val jsMeterProvider: JsMeterProvider = object : JsMeterProvider {
        override fun getMeter(
            name: String,
            version: String?,
            options: JsMeterOptions?
        ): JsMeter {
            return createNoopMeter()
        }
    }
    object : MeterProvider {

        override fun meterBuilder(instrumentationScopeName: String): MeterBuilder {
            return MeterBuilderImpl(instrumentationScopeName, jsMeterProvider)
        }
    }
}

actual interface OpenTelemetry {

    actual fun getTracerProvider(): TracerProvider
    actual fun getTracer(instrumentationScopeName: String): Tracer = getTracerProvider().get(instrumentationScopeName)

    actual fun tracerBuilder(instrumentationScopeName: String): TracerBuilder =
        getTracerProvider().tracerBuilder(instrumentationScopeName)

    actual fun getMeterProvider(): MeterProvider {
        return NoopMeterProvider
    }

    actual fun getMeter(instrumentationScopeName: String): Meter = getMeterProvider().get(instrumentationScopeName)

    actual fun meterBuilder(instrumentationScopeName: String): MeterBuilder = getMeterProvider().meterBuilder(instrumentationScopeName)

    actual fun getPropagators(): ContextPropagators
}

internal object OpenTelemetryImpl : OpenTelemetry {

    override fun getTracerProvider(): TracerProvider {
        return TracerProviderWrapper(JsTraceAPI.getTracerProvider())
    }

    override fun getMeterProvider(): MeterProvider {
        return MeterProviderWrapper(JsMetricsAPI.getMeterProvider())
    }
}
