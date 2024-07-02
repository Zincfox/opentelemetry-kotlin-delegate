package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.js.BaggageEntry as JsBaggageEntry
import io.opentelemetry.kotlindelegate.js.Baggage as JsBaggage

actual interface BaggageBuilder {

    actual fun put(
        key: String,
        value: String,
        entryMetadata: BaggageEntryMetadata,
    ): BaggageBuilder

    actual fun put(
        key: String,
        value: String,
    ): BaggageBuilder {
        return put(key, value, BaggageEntryMetadataStatic.empty())
    }

    actual fun remove(key: String): BaggageBuilder
    actual fun build(): Baggage
}

internal class BaggageBuilderImpl(jsBaggage: JsBaggage) : BaggageBuilder {
    var jsBaggage = jsBaggage
        private set
    override fun put(key: String, value: String, entryMetadata: BaggageEntryMetadata): BaggageBuilder {
        jsBaggage = jsBaggage.setEntry(key, js("{}").unsafeCast<JsBaggageEntry>().also {
            it.value = value
            it.metadata = entryMetadata.asNullableJsBaggageEntryMetadata() ?: return@also
        })
        return this
    }

    override fun put(key: String, value: String): BaggageBuilder {
        jsBaggage = jsBaggage.setEntry(key, js("{}").unsafeCast<JsBaggageEntry>().also {
            it.value = value
        })
        return this
    }

    override fun build(): Baggage {
        return BaggageWrapper(jsBaggage)
    }

    override fun remove(key: String): BaggageBuilder {
        jsBaggage = jsBaggage.removeEntry(key)
        return this
    }
}
