package io.opentelemetry.kotlindelegate.api.trace

expect object SpanIdWrapper {

    val Length: Int
    val Invalid: String
    fun isValid(spanId: CharSequence): Boolean
    fun fromBytes(spanIdBytes: ByteArray): String
    fun fromLong(id: Long): String
}
