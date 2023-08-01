package io.opentelemetry.kotlindelegate.utils

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetryWrapper
import io.opentelemetry.kotlindelegate.api.OpenTelemetryWrapper
import io.opentelemetry.kotlindelegate.api.metrics.MeterBuilderWrapper
import io.opentelemetry.kotlindelegate.api.metrics.MeterWrapper
import io.opentelemetry.kotlindelegate.api.metrics.buildMeter
import io.opentelemetry.kotlindelegate.api.trace.TracerBuilderWrapper
import io.opentelemetry.kotlindelegate.api.trace.TracerWrapper
import io.opentelemetry.kotlindelegate.api.trace.buildTracer

internal inline fun <reified T, reified R : WrapperBase<in T>> T.letWrapImmutable(block: T.() -> R): R = let(block)

fun <T> cachedWriteOnly(initial: T, setter: (T) -> Unit): CachedWriteOnlyAdapter<T> =
    CachedWriteOnlyAdapter(initial, setter)

fun <T:Nothing?> cachedWriteOnly(initial: T?=null, setter: (T?) -> Unit): CachedWriteOnlyAdapter<T?> =
    CachedWriteOnlyAdapter(initial, setter)


fun GlobalOpenTelemetryWrapper.getTracer(
    instrumentationScopeName: String,
): TracerWrapper = tracerProvider.get(
    instrumentationScopeName,
)

fun GlobalOpenTelemetryWrapper.getTracer(
    instrumentationScopeName: String,
    instrumentationScopeVersion: String,
): TracerWrapper = tracerProvider.get(
    instrumentationScopeName,
    instrumentationScopeVersion,
)

fun GlobalOpenTelemetryWrapper.tracerBuilder(
    instrumentationScopeName: String,
): TracerBuilderWrapper = tracerProvider.tracerBuilder(instrumentationScopeName)

fun GlobalOpenTelemetryWrapper.buildTracer(
    instrumentationScopeName: String,
    block: TracerBuilderWrapper.() -> Unit,
): TracerWrapper = tracerProvider.buildTracer(instrumentationScopeName, block)

fun GlobalOpenTelemetryWrapper.getMeter(
    instrumentationScopeName: String,
): MeterWrapper = meterProvider.get(
    instrumentationScopeName,
)

fun GlobalOpenTelemetryWrapper.meterBuilder(
    instrumentationScopeName: String,
): MeterBuilderWrapper = meterProvider.meterBuilder(instrumentationScopeName)

fun GlobalOpenTelemetryWrapper.buildMeter(
    instrumentationScopeName: String,
    block: MeterBuilderWrapper.() -> Unit,
): MeterWrapper = meterProvider.buildMeter(instrumentationScopeName, block)

fun OpenTelemetryWrapper.getTracer(
    instrumentationScopeName: String,
): TracerWrapper = tracerProvider.get(
    instrumentationScopeName,
)

fun OpenTelemetryWrapper.getTracer(
    instrumentationScopeName: String,
    instrumentationScopeVersion: String,
): TracerWrapper = tracerProvider.get(
    instrumentationScopeName,
    instrumentationScopeVersion,
)

fun OpenTelemetryWrapper.tracerBuilder(
    instrumentationScopeName: String,
): TracerBuilderWrapper = tracerProvider.tracerBuilder(instrumentationScopeName)

fun OpenTelemetryWrapper.buildTracer(
    instrumentationScopeName: String,
    block: TracerBuilderWrapper.() -> Unit,
): TracerWrapper = tracerProvider.buildTracer(instrumentationScopeName, block)

fun OpenTelemetryWrapper.getMeter(
    instrumentationScopeName: String,
): MeterWrapper = meterProvider.get(
    instrumentationScopeName,
)

fun OpenTelemetryWrapper.meterBuilder(
    instrumentationScopeName: String,
): MeterBuilderWrapper = meterProvider.meterBuilder(instrumentationScopeName)

fun OpenTelemetryWrapper.buildMeter(
    instrumentationScopeName: String,
    block: MeterBuilderWrapper.() -> Unit,
): MeterWrapper = meterProvider.buildMeter(instrumentationScopeName, block)
