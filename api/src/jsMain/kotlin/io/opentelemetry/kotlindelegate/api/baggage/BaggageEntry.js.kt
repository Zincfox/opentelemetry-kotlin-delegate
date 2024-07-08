package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.js.BaggageEntryMetadata as JsBaggageEntryMetadata
import io.opentelemetry.kotlindelegate.js.BaggageEntry as JsBaggageEntry

actual interface BaggageEntry {

    actual fun getValue(): String
    actual fun getMetadata(): BaggageEntryMetadata
}

internal class BaggageEntryWrapper(val entry: JsBaggageEntry) : BaggageEntry {

    constructor(value: String): this(js("{}").unsafeCast<JsBaggageEntry>().also {
        it.value = value
    })

    constructor(value: String, metadata: BaggageEntryMetadata): this(js("{}").unsafeCast<JsBaggageEntry>().also {
        it.value = value
        it.metadata = metadata.asNullableJsBaggageEntryMetadata() ?: return@also
    })
    constructor(value: String, metadata: JsBaggageEntryMetadata): this(js("{}").unsafeCast<JsBaggageEntry>().also {
        it.value = value
        it.metadata = metadata
    })
    override fun getValue(): String {
        return entry.value
    }

    override fun getMetadata(): BaggageEntryMetadata {
        return entry.metadata?.asCommonBaggageEntryMetadata() ?: BaggageEntryMetadataStatic.empty()
    }
}

fun BaggageEntry.asJsBaggageEntry(): JsBaggageEntry = when(this) {
    is BaggageEntryWrapper -> this.entry
    else -> js("{}").unsafeCast<JsBaggageEntry>().also {
        it.value = getValue()
        it.metadata = getMetadata().asNullableJsBaggageEntryMetadata() ?: return@also
    }
}

fun JsBaggageEntry.asCommonBaggageEntry(): BaggageEntry = BaggageEntryWrapper(this)
