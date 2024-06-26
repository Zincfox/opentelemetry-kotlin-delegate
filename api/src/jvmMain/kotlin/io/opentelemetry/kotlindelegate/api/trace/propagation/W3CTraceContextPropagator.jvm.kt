package io.opentelemetry.kotlindelegate.api.trace.propagation

import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator as OTEL_W3CTraceContextPropagator
import io.opentelemetry.context.propagation.TextMapPropagator

actual val W3CTraceContextPropagator: TextMapPropagator
    get() = OTEL_W3CTraceContextPropagator.getInstance()
