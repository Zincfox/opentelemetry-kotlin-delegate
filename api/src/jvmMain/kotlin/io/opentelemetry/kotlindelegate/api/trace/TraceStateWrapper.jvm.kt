package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.TraceState
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class TraceStateWrapper(
    override val wrappedObject: TraceState,
) : WrapperBase<TraceState>(), IWrapper, Map<String, String> {

    actual companion object {

        actual val Default: TraceStateWrapper by lazy {
            TraceState.getDefault().letWrapImmutable(::TraceStateWrapper)
        }

        actual fun builder(): TraceStateBuilderWrapper {
            return TraceState.builder().let(::TraceStateBuilderWrapper)
        }
    }

    actual fun toBuilder(): TraceStateBuilderWrapper {
        return wrappedObject.toBuilder().let(::TraceStateBuilderWrapper)
    }

    private val map: Map<String, String> by lazy {
        wrappedObject.asMap()
    }
    override val entries: Set<Map.Entry<String, String>>
        get() = map.entries
    override val keys: Set<String>
        get() = map.keys
    override val size: Int
        get() = map.size
    override val values: Collection<String>
        get() = map.values

    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    override fun get(key: String): String? {
        return map[key]
    }

    override fun containsValue(value: String): Boolean {
        return map.containsValue(value)
    }

    override fun containsKey(key: String): Boolean {
        return map.containsKey(key)
    }
}
