package io.opentelemetry.kotlindelegate.api.baggage.propagation

import io.opentelemetry.kotlindelegate.context.propagation.TextMapPropagator

expect val W3CBaggagePropagator: TextMapPropagator
