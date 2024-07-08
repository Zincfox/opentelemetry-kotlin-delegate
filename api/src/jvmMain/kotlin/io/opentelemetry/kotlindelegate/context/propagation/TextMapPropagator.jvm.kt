package io.opentelemetry.kotlindelegate.context.propagation

actual typealias TextMapPropagator = io.opentelemetry.context.propagation.TextMapPropagator
actual typealias TextMapGetter<C> = io.opentelemetry.context.propagation.TextMapGetter<C>
actual typealias TextMapSetter<C> = io.opentelemetry.context.propagation.TextMapSetter<C>

actual object TextMapPropagatorStatic {

    actual fun composite(vararg propagators: TextMapPropagator): TextMapPropagator = TextMapPropagator.composite(*propagators)

    actual fun composite(propagators: Iterable<TextMapPropagator>): TextMapPropagator = TextMapPropagator.composite(propagators)

    actual fun noop(): TextMapPropagator = TextMapPropagator.noop()
}
