package io.opentelemetry.kotlindelegate.api.trace

actual typealias TraceState = io.opentelemetry.api.trace.TraceState

actual object TraceStateStatic {

    actual fun getDefault(): TraceState = TraceState.getDefault()

    actual fun builder(): TraceStateBuilder = TraceState.builder()
}
