package io.opentelemetry.kotlindelegate.api.baggage

expect interface BaggageEntryMetadata {
    fun getValue(): String
}

expect object BaggageEntryMetadataStatic {
    fun empty(): BaggageEntryMetadata
    fun create(metadata: String): BaggageEntryMetadata
}
