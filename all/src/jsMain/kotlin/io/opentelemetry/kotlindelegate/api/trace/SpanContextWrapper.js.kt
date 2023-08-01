package io.opentelemetry.kotlindelegate.api.trace

actual class SpanContextWrapper : IWrapper {

    actual val traceId: String
        get() = TODO("Not yet implemented")

    actual fun getTraceIdBytes(): ByteArray {
        TODO("Not yet implemented")
    }

    actual val spanId: String
        get() = TODO("Not yet implemented")

    actual fun getSpanIdBytes(): ByteArray {
        TODO("Not yet implemented")
    }

    actual val isSampled: Boolean
        get() = TODO("Not yet implemented")
    actual val traceFlags: TraceFlagsWrapper
        get() = TODO("Not yet implemented")
    actual val traceState: TraceStateWrapper
        get() = TODO("Not yet implemented")
    actual val isValid: Boolean
        get() = TODO("Not yet implemented")
    actual val isRemote: Boolean
        get() = TODO("Not yet implemented")

    actual companion object {

        actual val invalid: SpanContextWrapper
            get() = TODO()

        actual fun create(
            traceIdHex: String,
            spanIdHex: String,
            traceFlags: TraceFlagsWrapper,
            traceState: TraceStateWrapper,
        ): SpanContextWrapper {
            TODO("Not yet implemented")
        }

        actual fun createFromRemoteParent(
            traceIdHex: String,
            spanIdHex: String,
            traceFlags: TraceFlagsWrapper,
            traceState: TraceStateWrapper,
        ): SpanContextWrapper {
            TODO("Not yet implemented")
        }
    }
}
