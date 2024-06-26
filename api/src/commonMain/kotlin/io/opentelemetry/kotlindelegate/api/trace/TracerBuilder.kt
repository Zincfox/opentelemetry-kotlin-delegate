package io.opentelemetry.kotlindelegate.api.trace

expect interface TracerBuilder {
    fun setSchemaUrl(schemaUrl: String): TracerBuilder
    fun setInstrumentationVersion(instrumentationScopeVersion: String): TracerBuilder
    fun build(): Tracer
}
