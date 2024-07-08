package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.Tracer as JsTracer

actual interface Tracer {

    actual fun spanBuilder(spanName: String): SpanBuilder
}

internal class TracerWrapper(val tracer: JsTracer): Tracer {

    override fun spanBuilder(spanName: String): SpanBuilder {
        return SpanBuilderImpl(spanName, tracer)
    }
}
