package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyed
import io.opentelemetry.kotlindelegate.context.asCommonContext
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.*
import io.opentelemetry.kotlindelegate.utils.java.BiConsumer
import io.opentelemetry.kotlindelegate.js.propagation as JsPropagationAPI
import io.opentelemetry.kotlindelegate.js.Baggage as JsBaggage
import io.opentelemetry.kotlindelegate.js.BaggageEntry as JsBaggageEntry

actual interface Baggage : ImplicitContextKeyed {

    actual override fun storeInContext(context: Context): Context {
        return JsPropagationAPI.setBaggage(context.asJsContext(), this.asJsBaggage()).asCommonContext()
    }

    actual fun size(): Int
    actual fun isEmpty(): Boolean = size() > 0

    actual fun forEach(consumer: BiConsumer<in String, in BaggageEntry>)
    actual fun asMap(): Map<String, BaggageEntry>
    actual fun getEntryValue(entryKey: String): String?
    actual fun toBuilder(): BaggageBuilder
}

internal class BaggageWrapper(val jsBaggage: JsBaggage): Baggage {

    override fun size(): Int {
        return jsBaggage.getAllEntries().size
    }

    override fun forEach(consumer: BiConsumer<in String, in BaggageEntry>) {
        return jsBaggage.getAllEntries().forEach { pair -> consumer.accept(pair.first, pair.second.asCommonBaggageEntry()) }
    }

    override fun asMap(): Map<String, BaggageEntry> {
        return jsBaggage.getAllEntries().associate { it.first to it.second.asCommonBaggageEntry() }
    }

    override fun getEntryValue(entryKey: String): String? {
        return jsBaggage.getEntry(entryKey)?.value
    }

    override fun toBuilder(): BaggageBuilder {
        return BaggageBuilderImpl(jsBaggage)
    }
}

fun JsBaggage.asCommonBaggage(): Baggage = BaggageWrapper(this)
fun Baggage.asJsBaggage(): JsBaggage = when(this) {
    is BaggageWrapper -> this.jsBaggage
    else -> JsPropagationAPI.createBaggage((js("{}").unsafeCast<JsRecord<String, JsBaggageEntry>>().also { record ->
        forEach { key, entry ->
            record[key] = entry.asJsBaggageEntry()
        }
    }))
}

actual object BaggageStatic {

    private val emptyBaggage: Baggage = BaggageWrapper(JsPropagationAPI.createBaggage())

    actual fun empty(): Baggage {
        return emptyBaggage
    }

    actual fun builder(): BaggageBuilder {
        return emptyBaggage.toBuilder()
    }

    actual fun current(): Baggage {
        val baggage = JsPropagationAPI.getActiveBaggage() ?: return empty()
        return BaggageWrapper(baggage)
    }

    actual fun fromContext(context: Context): Baggage {
        val baggage = JsPropagationAPI.getBaggage(context.asJsContext()) ?: return empty()
        return BaggageWrapper(baggage)
    }

    actual fun fromContextOrNull(context: Context): Baggage? {
        val baggage = JsPropagationAPI.getBaggage(context.asJsContext()) ?: return null
        return BaggageWrapper(baggage)
    }
}
