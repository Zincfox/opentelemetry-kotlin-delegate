package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.java.BiConsumer


expect interface TraceState {
    fun get(key: String): String?
    fun size(): Int
    fun isEmpty(): Boolean
    fun forEach(consumer: BiConsumer<String, String>)
    fun asMap(): Map<String, String>
    fun toBuilder(): TraceStateBuilder
}

expect object TraceStateStatic {
    fun getDefault(): TraceState
    fun builder(): TraceStateBuilder
}
