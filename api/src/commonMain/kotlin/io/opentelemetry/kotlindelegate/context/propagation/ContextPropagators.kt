package io.opentelemetry.kotlindelegate.context.propagation


expect interface ContextPropagators {
    fun getTextMapPropagator(): TextMapPropagator
}

expect object ContextPropagatorsStatic {
    fun noop(): ContextPropagators
    fun create(textPropagator: TextMapPropagator): ContextPropagators
}
