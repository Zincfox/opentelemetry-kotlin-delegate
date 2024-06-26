package io.opentelemetry.kotlindelegate.api.baggage

actual typealias BaggageEntryMetadata = io.opentelemetry.api.baggage.BaggageEntryMetadata

actual object BaggageEntryMetadataStatic {

    actual fun empty(): BaggageEntryMetadata = BaggageEntryMetadata.empty()

    actual fun create(metadata: String): BaggageEntryMetadata = BaggageEntryMetadata.create(metadata)
}
