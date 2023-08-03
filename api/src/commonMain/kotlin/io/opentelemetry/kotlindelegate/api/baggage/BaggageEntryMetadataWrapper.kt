package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class BaggageEntryMetadataWrapper : IWrapper {
    companion object {

        val empty: BaggageEntryMetadataWrapper
        fun create(metadata: String): BaggageEntryMetadataWrapper
    }

    val value: String
}
