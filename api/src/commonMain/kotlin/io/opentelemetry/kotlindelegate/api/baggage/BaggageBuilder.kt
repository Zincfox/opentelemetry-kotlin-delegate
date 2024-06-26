package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.api.baggage.BaggageEntryMetadata

expect interface BaggageBuilder {
    fun put(key: String, value: String, entryMetadata: BaggageEntryMetadata): BaggageBuilder
    open fun put(key: String, value: String): BaggageBuilder
    fun remove(key: String): BaggageBuilder
    fun build(): Baggage
}
