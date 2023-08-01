package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class BaggageEntryWrapper : IWrapper {

    val value: String
    val metadata: BaggageEntryMetadataWrapper
}
