package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.SpanId

actual object SpanIdWrapper {

    actual val Length: Int = SpanId.getLength()

    actual val Invalid: String = SpanId.getInvalid()

    actual fun isValid(spanId: CharSequence): Boolean {
        return SpanId.isValid(spanId)
    }

    actual fun fromBytes(spanIdBytes: ByteArray): String {
        return SpanId.fromBytes(spanIdBytes)
    }

    actual fun fromLong(id: Long): String {
        return SpanId.fromLong(id)
    }
}
