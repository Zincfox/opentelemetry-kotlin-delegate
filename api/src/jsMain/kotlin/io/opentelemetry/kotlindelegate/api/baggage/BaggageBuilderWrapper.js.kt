package io.opentelemetry.kotlindelegate.api.baggage

actual class BaggageBuilderWrapper {

    actual fun put(
        key: String,
        value: String,
        entryMetadata: BaggageEntryMetadataWrapper,
    ) {
    }

    actual fun put(key: String, value: String) {
    }

    actual fun remove(key: String) {
    }

    actual fun build(): BaggageWrapper {
        TODO("Not yet implemented")
    }
}
