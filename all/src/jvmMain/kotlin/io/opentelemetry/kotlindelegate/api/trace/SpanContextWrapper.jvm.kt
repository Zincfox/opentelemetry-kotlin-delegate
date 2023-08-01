package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class SpanContextWrapper(
    override val wrappedObject: SpanContext
) : WrapperBase<SpanContext>(), IWrapper {

    actual val traceId: String = wrappedObject.traceId

    actual fun getTraceIdBytes(): ByteArray {
        return wrappedObject.traceIdBytes
    }

    actual val spanId: String = wrappedObject.spanId

    actual fun getSpanIdBytes(): ByteArray {
        return wrappedObject.spanIdBytes
    }

    actual val isSampled: Boolean = wrappedObject.isSampled
    actual val traceFlags: TraceFlagsWrapper by lazy {
        wrappedObject.traceFlags.letWrapImmutable(::TraceFlagsWrapper)
    }
    actual val traceState: TraceStateWrapper by lazy {
        wrappedObject.traceState.letWrapImmutable(::TraceStateWrapper)
    }
    actual val isValid: Boolean = wrappedObject.isValid
    actual val isRemote: Boolean = wrappedObject.isRemote

    actual companion object {

        actual val invalid: SpanContextWrapper by lazy {
            SpanContext.getInvalid().letWrapImmutable(::SpanContextWrapper)
        }

        actual fun create(
            traceIdHex: String,
            spanIdHex: String,
            traceFlags: TraceFlagsWrapper,
            traceState: TraceStateWrapper,
        ): SpanContextWrapper {
            return SpanContext.create(
                traceIdHex,
                spanIdHex,
                traceFlags.wrappedObject,
                traceState.wrappedObject
            ).letWrapImmutable(::SpanContextWrapper)
        }

        actual fun createFromRemoteParent(
            traceIdHex: String,
            spanIdHex: String,
            traceFlags: TraceFlagsWrapper,
            traceState: TraceStateWrapper,
        ): SpanContextWrapper {
            return SpanContext.create(
                traceIdHex,
                spanIdHex,
                traceFlags.wrappedObject,
                traceState.wrappedObject
            ).letWrapImmutable(::SpanContextWrapper)
        }
    }
}
