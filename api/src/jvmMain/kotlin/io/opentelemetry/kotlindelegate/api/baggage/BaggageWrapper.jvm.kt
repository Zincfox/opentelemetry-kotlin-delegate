package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.api.baggage.Baggage
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyedWrapper
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class BaggageWrapper(
    override val wrappedObject: Baggage,
) : ImplicitContextKeyedWrapper(wrappedObject), Map<String, BaggageEntryWrapper> {

    actual companion object {

        actual val empty: BaggageWrapper by lazy {
            Baggage.empty().letWrapImmutable(::BaggageWrapper)
        }

        actual fun current(): BaggageWrapper {
            return Baggage.current().letWrapImmutable(::BaggageWrapper)
        }

        actual fun builder(): BaggageBuilderWrapper {
            return Baggage.builder().let(::BaggageBuilderWrapper)
        }

        actual fun fromContext(context: ContextWrapper): BaggageWrapper {
            return Baggage.fromContext(context.wrappedObject).letWrapImmutable(::BaggageWrapper)
        }

        actual fun fromContextOrNull(context: ContextWrapper): BaggageWrapper? {
            return Baggage.fromContextOrNull(context.wrappedObject)?.letWrapImmutable(::BaggageWrapper)
        }
    }

    actual fun getEntryValue(entryKey: String): String? {
        return wrappedObject.getEntryValue(entryKey)
    }

    actual fun toBuilder(): BaggageBuilderWrapper {
        return wrappedObject.toBuilder().let(::BaggageBuilderWrapper)
    }

    private val map: Map<String, BaggageEntryWrapper> by lazy {
        wrappedObject.asMap().mapValues { it.value.letWrapImmutable(::BaggageEntryWrapper) }
    }

    override val entries: Set<Map.Entry<String, BaggageEntryWrapper>> by lazy { map.entries }
    override val keys: Set<String> by lazy { map.keys }
    override val size: Int by lazy { map.size }
    override val values: Collection<BaggageEntryWrapper> by lazy { map.values }

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun get(key: String): BaggageEntryWrapper? {
        return map[key]
    }

    override fun containsValue(value: BaggageEntryWrapper): Boolean {
        return map.containsValue(value)
    }

    override fun containsKey(key: String): Boolean {
        return map.containsKey(key)
    }
}
