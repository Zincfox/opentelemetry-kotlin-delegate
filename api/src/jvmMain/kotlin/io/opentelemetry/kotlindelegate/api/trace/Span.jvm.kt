package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context
import java.util.concurrent.TimeUnit

actual typealias Span = io.opentelemetry.api.trace.Span

actual fun Span.addEventMillis(
    name: String,
    attributes: Attributes,
    timestampMillis: Long,
): Span = addEvent(name, attributes, timestampMillis, TimeUnit.MILLISECONDS)

actual fun Span.addEventMillis(
    name: String,
    timestampMillis: Long,
): Span = addEvent(name, timestampMillis, TimeUnit.MILLISECONDS)

actual fun Span.addEventNanos(
    name: String,
    attributes: Attributes,
    timestampNanos: Long,
): Span = addEvent(name, attributes, timestampNanos, TimeUnit.NANOSECONDS)

actual fun Span.addEventNanos(
    name: String,
    timestampNanos: Long,
): Span = addEvent(name, timestampNanos, TimeUnit.NANOSECONDS)

actual fun Span.endMillis(timestampMillis: Long) = end(timestampMillis, TimeUnit.MILLISECONDS)

actual fun Span.endNanos(timestampNanos: Long) = end(timestampNanos, TimeUnit.NANOSECONDS)

actual object SpanStatic {

    actual fun current(): Span = Span.current()

    actual fun fromContext(context: Context): Span = Span.fromContext(context)

    actual fun fromContextOrNull(context: Context): Span? = Span.fromContextOrNull(context)

    actual fun getInvalid(): Span = Span.getInvalid()

    actual fun wrap(spanContext: SpanContext): Span = Span.wrap(spanContext)
}
