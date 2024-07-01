package io.opentelemetry.kotlindelegate.api.trace

expect interface TracerProvider {
    fun get(instrumentationScopeName: String): Tracer
    fun get(instrumentationScopeName: String, instrumentationScopeVersion: String): Tracer
    open fun tracerBuilder(instrumentationScopeName: String): TracerBuilder
}
