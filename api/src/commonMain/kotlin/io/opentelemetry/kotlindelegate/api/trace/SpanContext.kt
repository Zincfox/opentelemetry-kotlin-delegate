package io.opentelemetry.kotlindelegate.api.trace

expect interface SpanContext {

    fun getTraceId(): String
    open fun getTraceIdBytes(): ByteArray
    fun getSpanId(): String
    open fun getSpanIdBytes(): ByteArray
    open fun isSampled(): Boolean
    fun getTraceFlags(): TraceFlags
    fun getTraceState(): TraceState
    open fun isValid(): Boolean
    fun isRemote(): Boolean
}

expect object SpanContextStatic {
    fun getInvalid(): SpanContext

    fun create(
        traceIdHex: String,
        spanIdHex: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
    ): SpanContext

    fun createFromRemoteParent(
        traceIdHex: String,
        spanIdHex: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
    ): SpanContext
}
