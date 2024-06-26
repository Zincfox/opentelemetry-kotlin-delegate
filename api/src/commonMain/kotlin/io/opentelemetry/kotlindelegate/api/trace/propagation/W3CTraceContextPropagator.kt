package io.opentelemetry.kotlindelegate.api.trace.propagation

import io.opentelemetry.kotlindelegate.context.propagation.TextMapPropagator

expect val W3CTraceContextPropagator: TextMapPropagator
