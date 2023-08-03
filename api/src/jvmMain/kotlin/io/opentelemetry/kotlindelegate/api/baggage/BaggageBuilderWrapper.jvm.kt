package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.api.baggage.BaggageBuilder
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class BaggageBuilderWrapper(
    override val wrappedObject: BaggageBuilder
) : WrapperBase<BaggageBuilder>(), IWrapper {

    actual fun put(
        key: String,
        value: String,
        entryMetadata: BaggageEntryMetadataWrapper,
    ): BaggageBuilderWrapper {
        wrappedObject.put(key, value, entryMetadata.wrappedObject)
        return this
    }

    actual fun put(key: String, value: String): BaggageBuilderWrapper {
        wrappedObject.put(key, value)
        return this
    }

    actual fun remove(key: String): BaggageBuilderWrapper {
        wrappedObject.remove(key)
        return this
    }

    actual fun build(): BaggageWrapper {
        return wrappedObject.build().letWrapImmutable(::BaggageWrapper)
    }
}
