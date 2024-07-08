package io.opentelemetry.kotlindelegate.api.trace

expect interface Tracer {
    fun spanBuilder(spanName: String): SpanBuilder
}
