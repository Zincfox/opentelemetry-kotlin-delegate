package io.opentelemetry.kotlindelegate.api.baggage.propagation

import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator as OTEL_W3CBaggagePropagator
import io.opentelemetry.kotlindelegate.context.propagation.TextMapPropagator

actual val W3CBaggagePropagator: TextMapPropagator
    get() = OTEL_W3CBaggagePropagator.getInstance()
