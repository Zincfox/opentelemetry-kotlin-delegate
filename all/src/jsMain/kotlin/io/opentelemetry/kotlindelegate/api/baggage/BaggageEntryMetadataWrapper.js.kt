package io.opentelemetry.kotlindelegate.api.baggage

actual class BaggageEntryMetadataWrapper : IWrapper {
    actual companion object {

        actual val empty: BaggageEntryMetadataWrapper
            get() {
                TODO("Not yet implemented")
            }

        actual fun create(metadata: String): BaggageEntryMetadataWrapper {
            TODO("Not yet implemented")
        }
    }

    actual val value: String
        get() = TODO("Not yet implemented")
}
