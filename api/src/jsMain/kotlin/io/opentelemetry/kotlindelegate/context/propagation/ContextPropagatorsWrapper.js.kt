package io.opentelemetry.kotlindelegate.context.propagation

actual class ContextPropagatorsWrapper : IWrapper {
    actual companion object {

        actual fun create(textPropagator: TextMapPropagatorWrapper): ContextPropagatorsWrapper {
            TODO("Not yet implemented")
        }

        actual val Noop: ContextPropagatorsWrapper
            get() = TODO("Not yet implemented")
    }

    actual val textMapPropagator: TextMapPropagatorWrapper
        get() = TODO("Not yet implemented")
}
