package io.opentelemetry.kotlindelegate.api.metrics

expect interface MeterBuilder {
    fun setSchemaUrl(schemaUrl: String): MeterBuilder
    fun setInstrumentationVersion(instrumentationVersion: String): MeterBuilder
    fun build(): Meter
}
