package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.api.trace.Span

interface TraceAsserter {
    val traceId: String
    val children: List<Span>
}
