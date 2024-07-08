package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.js.baggageEntryMetadataFromString
import io.opentelemetry.kotlindelegate.js.BaggageEntryMetadata as JsBaggageEntryMetadata

actual interface BaggageEntryMetadata {

    actual fun getValue(): String
}

internal class BaggageEntryMetadataWrapper(val baggageEntryMetadata: JsBaggageEntryMetadata) : BaggageEntryMetadata {

    override fun getValue(): String {
        return baggageEntryMetadata.toString()
    }
}

fun BaggageEntryMetadata.asNullableJsBaggageEntryMetadata(): JsBaggageEntryMetadata? {
    val value = getValue()
    return when {
        value.isEmpty() -> null
        this is BaggageEntryMetadataWrapper -> this.baggageEntryMetadata
        else -> baggageEntryMetadataFromString(value)
    }
}

fun BaggageEntryMetadata.asJsBaggageEntryMetadata(): JsBaggageEntryMetadata = when (this) {
    is BaggageEntryMetadataWrapper -> this.baggageEntryMetadata
    else -> baggageEntryMetadataFromString(this.getValue())
}

fun JsBaggageEntryMetadata.asCommonBaggageEntryMetadata(): BaggageEntryMetadata = BaggageEntryMetadataWrapper(this)

actual object BaggageEntryMetadataStatic {

    val emptyMetadata: BaggageEntryMetadata = BaggageEntryMetadataWrapper(baggageEntryMetadataFromString(""))

    actual fun empty(): BaggageEntryMetadata {
        return emptyMetadata
    }

    actual fun create(metadata: String): BaggageEntryMetadata {
        return BaggageEntryMetadataWrapper(baggageEntryMetadataFromString(metadata))
    }
}
