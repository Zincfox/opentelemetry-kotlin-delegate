package io.opentelemetry.kotlindelegate.api.baggage.propagation

import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator
import io.opentelemetry.kotlindelegate.context.propagation.TextMapPropagatorWrapper
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual val W3CBaggagePropagatorWrapper: TextMapPropagatorWrapper by lazy {
    W3CBaggagePropagator.getInstance().letWrapImmutable(::TextMapPropagatorWrapper)
}
