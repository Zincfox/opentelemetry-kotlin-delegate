package io.opentelemetry.kotlindelegate.api.trace

actual object SpanIdWrapper {

    actual val Length: Int
        get() = TODO("Not yet implemented")

    actual val Invalid: String
        get() = TODO("Not yet implemented")

    actual fun isValid(spanId: CharSequence): Boolean {
        TODO("Not yet implemented")
    }

    actual fun fromBytes(spanIdBytes: ByteArray): String {
        TODO("Not yet implemented")
    }

    actual fun fromLong(id: Long): String {
        TODO("Not yet implemented")
    }
}
