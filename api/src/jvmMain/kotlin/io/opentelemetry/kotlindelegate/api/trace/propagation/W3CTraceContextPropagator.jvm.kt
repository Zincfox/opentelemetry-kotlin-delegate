package io.opentelemetry.kotlindelegate.api.trace.propagation

import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.kotlindelegate.context.propagation.TextMapPropagatorWrapper

actual val W3CTraceContextPropagatorWrapper: TextMapPropagatorWrapper by lazy {
    TextMapPropagatorWrapper(W3CTraceContextPropagator.getInstance())
}
