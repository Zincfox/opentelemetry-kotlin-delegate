package io.opentelemetry.kotlindelegate.api.trace

expect object TraceIdWrapper {

    val Length: Int
    val Invalid: String
    fun isValid(spanId: CharSequence): Boolean
    fun fromBytes(spanIdBytes: ByteArray): String
    fun fromLongs(traceIdLongHighPart: Long, traceIdLongLowPart: Long): String
}
