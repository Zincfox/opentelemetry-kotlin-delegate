package io.opentelemetry.kotlindelegate.utils

import io.opentelemetry.kotlindelegate.api.trace.Span
import io.opentelemetry.kotlindelegate.api.trace.SpanBuilder
import io.opentelemetry.kotlindelegate.api.trace.Tracer

inline fun Tracer.startSpan(spanName: String, builderBlock: SpanBuilder.() -> Unit = {}): Span =
    spanBuilder(spanName).apply(builderBlock).startSpan()
