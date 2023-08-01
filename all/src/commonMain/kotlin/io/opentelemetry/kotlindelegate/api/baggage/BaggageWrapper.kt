package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyedWrapper

expect class BaggageWrapper : ImplicitContextKeyedWrapper, Map<String, BaggageEntryWrapper> {
    companion object {

        val empty: BaggageWrapper
        fun current(): BaggageWrapper
        fun builder(): BaggageBuilderWrapper
        fun fromContext(context: ContextWrapper): BaggageWrapper
        fun fromContextOrNull(context: ContextWrapper): BaggageWrapper?
    }

    fun getEntryValue(entryKey: String): String?
    fun toBuilder(): BaggageBuilderWrapper
}

fun BaggageWrapper.Companion.build(
    configuration: BaggageBuilderWrapper.() -> Unit,
): BaggageWrapper = builder().apply(configuration).build()
