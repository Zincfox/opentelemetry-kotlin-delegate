package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class SpanContextWrapper : IWrapper {

    val traceId: String
    fun getTraceIdBytes(): ByteArray
    val spanId: String
    fun getSpanIdBytes(): ByteArray
    val isSampled: Boolean
    val traceFlags: TraceFlagsWrapper
    val traceState: TraceStateWrapper
    val isValid: Boolean
    val isRemote: Boolean

    companion object {

        val invalid: SpanContextWrapper
        fun create(
            traceIdHex: String,
            spanIdHex: String,
            traceFlags: TraceFlagsWrapper,
            traceState: TraceStateWrapper,
        ): SpanContextWrapper

        fun createFromRemoteParent(
            traceIdHex: String,
            spanIdHex: String,
            traceFlags: TraceFlagsWrapper,
            traceState: TraceStateWrapper,
        ): SpanContextWrapper
    }
}
