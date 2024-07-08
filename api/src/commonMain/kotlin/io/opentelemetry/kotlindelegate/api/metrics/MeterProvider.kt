package io.opentelemetry.kotlindelegate.api.metrics

expect interface MeterProvider {
    open fun get(instrumentationScopeName: String): Meter
    fun meterBuilder(instrumentationScopeName: String): MeterBuilder
}
