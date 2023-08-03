package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class BaggageBuilderWrapper : IWrapper {

    fun put(key: String, value: String, entryMetadata: BaggageEntryMetadataWrapper): BaggageBuilderWrapper
    fun put(key: String, value: String): BaggageBuilderWrapper
    fun remove(key: String): BaggageBuilderWrapper
    fun build(): BaggageWrapper
}
