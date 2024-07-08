package io.opentelemetry.kotlindelegate.api.baggage

expect interface BaggageEntry {

    fun getValue(): String
    fun getMetadata(): BaggageEntryMetadata
}
