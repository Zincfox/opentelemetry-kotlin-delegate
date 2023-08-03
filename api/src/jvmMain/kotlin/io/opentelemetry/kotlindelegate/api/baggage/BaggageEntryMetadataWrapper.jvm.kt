package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.api.baggage.BaggageEntryMetadata
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class BaggageEntryMetadataWrapper(
    override val wrappedObject: BaggageEntryMetadata,
) : WrapperBase<BaggageEntryMetadata>(), IWrapper {

    actual companion object {

        actual val empty: BaggageEntryMetadataWrapper by lazy {
            BaggageEntryMetadata.empty().letWrapImmutable(::BaggageEntryMetadataWrapper)
        }

        actual fun create(metadata: String): BaggageEntryMetadataWrapper {
            return BaggageEntryMetadata.create(metadata).letWrapImmutable(::BaggageEntryMetadataWrapper)
        }
    }

    actual val value: String
        get() = wrappedObject.value
}
