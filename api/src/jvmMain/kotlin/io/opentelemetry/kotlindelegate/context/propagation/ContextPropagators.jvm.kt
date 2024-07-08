package io.opentelemetry.kotlindelegate.context.propagation

actual typealias ContextPropagators = io.opentelemetry.context.propagation.ContextPropagators

actual object ContextPropagatorsStatic {

    actual fun noop(): ContextPropagators = ContextPropagators.noop()

    actual fun create(textPropagator: TextMapPropagator): ContextPropagators = ContextPropagators.create(textPropagator)
}
