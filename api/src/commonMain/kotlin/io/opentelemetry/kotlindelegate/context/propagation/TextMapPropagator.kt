package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.context.Context

expect interface TextMapPropagator {
    fun fields(): Collection<String>
    fun <C: Any> inject(context: Context, carrier: C?, setter: TextMapSetter<C>)
    fun <C: Any> extract(context: Context, carrier: C?, getter: TextMapGetter<C>): Context
}

expect object TextMapPropagatorStatic {
    fun composite(vararg propagators: TextMapPropagator): TextMapPropagator
    fun composite(propagators: Iterable<TextMapPropagator>): TextMapPropagator
    fun noop(): TextMapPropagator
}
