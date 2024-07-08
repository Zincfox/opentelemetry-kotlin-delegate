package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyed
import io.opentelemetry.kotlindelegate.utils.java.BiConsumer

expect interface Baggage : ImplicitContextKeyed {
    open override fun storeInContext(context: Context): Context
    fun size(): Int
    open fun isEmpty(): Boolean
    fun forEach(consumer: BiConsumer<in String, in BaggageEntry>)
    fun asMap(): Map<String, BaggageEntry>
    fun getEntryValue(entryKey: String): String?
    fun toBuilder(): BaggageBuilder
}

expect object BaggageStatic {
    fun empty(): Baggage
    fun builder(): BaggageBuilder
    fun current(): Baggage
    fun fromContext(context: Context): Baggage
    fun fromContextOrNull(context: Context): Baggage?
}
