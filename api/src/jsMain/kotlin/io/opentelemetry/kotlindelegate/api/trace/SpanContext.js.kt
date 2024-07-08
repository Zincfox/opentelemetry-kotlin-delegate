package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.INVALID_SPAN_CONTEXT
import io.opentelemetry.kotlindelegate.js.createTraceState
import io.opentelemetry.kotlindelegate.js.isValidSpanId
import io.opentelemetry.kotlindelegate.js.isValidTraceId
import io.opentelemetry.kotlindelegate.js.SpanContext as JsSpanContext
import io.opentelemetry.kotlindelegate.js.TraceState as JsTraceState

actual interface SpanContext {

    actual fun getTraceId(): String
    actual fun getTraceIdBytes(): ByteArray = getTraceId().hexToByteArray()

    actual fun getSpanId(): String
    actual fun getSpanIdBytes(): ByteArray = getSpanId().hexToByteArray()

    actual fun isSampled(): Boolean {
        return getTraceFlags().isSampled()
    }

    actual fun getTraceFlags(): TraceFlags
    actual fun getTraceState(): TraceState
    actual fun isValid(): Boolean {
        return isValidTraceId(getTraceId()) && isValidSpanId(getSpanId())
    }

    actual fun isRemote(): Boolean
}


internal class SpanContextWrapper(val jsSpanContext: JsSpanContext) : SpanContext {

    override fun getTraceId(): String {
        return jsSpanContext.traceId
    }

    override fun getSpanId(): String {
        return jsSpanContext.spanId
    }

    override fun getTraceFlags(): TraceFlags {
        return TraceFlagsStatic.fromByte(jsSpanContext.traceFlags)
    }

    override fun getTraceState(): TraceState {
        return TraceStateCommonImpl(jsSpanContext.traceState ?: createTraceState())
    }

    override fun isRemote(): Boolean {
        return (jsSpanContext.isRemote == true)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpanContext) return false
        return if (other is SpanContextWrapper) {
            (jsSpanContext.spanId == other.jsSpanContext.spanId) &&
                    (jsSpanContext.traceId == other.jsSpanContext.traceId) &&
                    (jsSpanContext.traceFlags == other.jsSpanContext.traceFlags) &&
                    ((jsSpanContext.isRemote ?: false) == (other.jsSpanContext.isRemote ?: false)) &&
                    ((jsSpanContext.traceState == other.jsSpanContext.traceState) ||
                            (jsSpanContext.traceState?.serialize().orEmpty() ==
                                    other.jsSpanContext.traceState?.serialize().orEmpty()))
        } else {
            (jsSpanContext.spanId == other.getSpanId()) &&
                    (jsSpanContext.traceId == other.getTraceId()) &&
                    (jsSpanContext.traceFlags == other.getTraceFlags().asByte()) &&
                    ((jsSpanContext.isRemote ?: false) == other.isRemote()) &&
                    (jsSpanContext.traceState?.serialize().orEmpty() ==
                            other.getTraceState().asJsTraceState().serialize())
        }
    }

    override fun hashCode(): Int {
        var hash = jsSpanContext.spanId.hashCode()
        hash = hash * 31 + jsSpanContext.traceId.hashCode()
        hash = hash * 31 + jsSpanContext.traceFlags.hashCode()
        hash = hash * 31 + jsSpanContext.isRemote.hashCode()
        return hash
    }
}

fun SpanContext.asJsSpanContext(): JsSpanContext = when (this) {
    is SpanContextWrapper -> jsSpanContext
    else -> SpanContextStatic.createJs(
        getTraceId(),
        getSpanId(),
        getTraceFlags().asByte(),
        getTraceState().asJsTraceState(),
        isRemote(),
    )
}

actual object SpanContextStatic {

    actual fun getInvalid(): SpanContext {
        return SpanContextWrapper(INVALID_SPAN_CONTEXT)
    }

    fun createJs(
        traceIdHex: String,
        spanIdHex: String,
        traceFlags: Byte,
        traceState: JsTraceState,
        remote: Boolean,
    ): JsSpanContext {
        return js("{}").unsafeCast<JsSpanContext>().also {
            it.traceId = traceIdHex
            it.spanId = spanIdHex
            it.traceFlags = traceFlags
            it.traceState = traceState
            it.isRemote = remote
        }
    }

    actual fun create(
        traceIdHex: String,
        spanIdHex: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
    ): SpanContext {
        return SpanContextWrapper(
            createJs(
                traceIdHex,
                spanIdHex,
                traceFlags.asByte(),
                traceState.asJsTraceState(),
                false
            )
        )
    }

    actual fun createFromRemoteParent(
        traceIdHex: String,
        spanIdHex: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
    ): SpanContext {
        return SpanContextWrapper(
            createJs(
                traceIdHex,
                spanIdHex,
                traceFlags.asByte(),
                traceState.asJsTraceState(),
                true
            )
        )
    }
}
