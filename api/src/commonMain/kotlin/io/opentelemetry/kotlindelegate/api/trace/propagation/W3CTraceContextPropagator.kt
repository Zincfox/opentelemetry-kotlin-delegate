package io.opentelemetry.kotlindelegate.api.trace.propagation

import io.opentelemetry.context.propagation.TextMapPropagator

expect val W3CTraceContextPropagator: TextMapPropagator
