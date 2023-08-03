package io.opentelemetry.kotlindelegate.api.baggage

actual class BaggageEntryWrapper : IWrapper {

    actual val value: String
        get() = TODO("Not yet implemented")
    actual val metadata: BaggageEntryMetadataWrapper
        get() = TODO("Not yet implemented")
}
