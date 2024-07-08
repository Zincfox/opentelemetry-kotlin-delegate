package io.opentelemetry.kotlindelegate.api.trace

expect interface TraceStateBuilder {
    fun put(key: String, value: String): TraceStateBuilder
    fun remove(key: String): TraceStateBuilder
    fun build(): TraceState
}
