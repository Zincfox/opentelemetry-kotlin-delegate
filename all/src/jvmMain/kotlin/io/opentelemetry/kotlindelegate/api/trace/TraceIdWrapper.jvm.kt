package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.TraceId

actual object TraceIdWrapper {

    actual val Length: Int = TraceId.getLength()

    actual val Invalid: String = TraceId.getInvalid()

    actual fun isValid(spanId: CharSequence): Boolean {
        return TraceId.isValid(spanId)
    }

    actual fun fromBytes(spanIdBytes: ByteArray): String {
        return TraceId.fromBytes(spanIdBytes)
    }

    actual fun fromLongs(traceIdLongHighPart: Long, traceIdLongLowPart: Long): String {
        return TraceId.fromLongs(traceIdLongHighPart, traceIdLongLowPart)
    }
}
