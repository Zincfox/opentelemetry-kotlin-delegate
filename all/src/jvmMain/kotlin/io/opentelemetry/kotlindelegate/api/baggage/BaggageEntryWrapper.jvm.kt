package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.api.baggage.BaggageEntry
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class BaggageEntryWrapper(
    override val wrappedObject: BaggageEntry,
) : WrapperBase<BaggageEntry>(), IWrapper {

    actual val value: String
        get() = wrappedObject.value
    actual val metadata: BaggageEntryMetadataWrapper
        get() = wrappedObject.metadata.letWrapImmutable(::BaggageEntryMetadataWrapper)
}
