package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.TraceState as JsTraceState

actual interface TraceStateBuilder {

    actual fun put(
        key: String,
        value: String,
    ): TraceStateBuilder

    actual fun remove(key: String): TraceStateBuilder
    actual fun build(): TraceState
}

internal class TraceStateBuilderImpl(
    var jsTraceState: JsTraceState = TraceStateStatic.defaultJsTraceState
) : TraceStateBuilder {

    override fun remove(key: String): TraceStateBuilder {
        jsTraceState = jsTraceState.unset(key)
        return this
    }

    override fun put(key: String, value: String): TraceStateBuilder {
        jsTraceState = jsTraceState.set(key, value)
        return this
    }

    override fun build(): TraceState = TraceStateCommonImpl(jsTraceState)
}
