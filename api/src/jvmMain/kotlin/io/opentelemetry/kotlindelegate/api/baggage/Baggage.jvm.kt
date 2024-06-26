package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.context.Context

actual typealias Baggage = io.opentelemetry.api.baggage.Baggage

actual object BaggageStatic {

    actual fun empty(): Baggage = Baggage.empty()

    actual fun builder(): BaggageBuilder = Baggage.builder()

    actual fun current(): Baggage = Baggage.current()

    actual fun fromContext(context: Context): Baggage = Baggage.fromContext(context)

    actual fun fromContextOrNull(context: Context): Baggage? = Baggage.fromContextOrNull(context)
}
