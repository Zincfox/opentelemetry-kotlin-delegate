package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class ContextPropagatorsWrapper(
    override val wrappedObject: ContextPropagators
) : WrapperBase<ContextPropagators>(), IWrapper {
    actual companion object {

        actual fun create(textPropagator: TextMapPropagatorWrapper): ContextPropagatorsWrapper {
            return ContextPropagatorsWrapper(ContextPropagators.create(textPropagator.wrappedObject))
        }

        actual val Noop: ContextPropagatorsWrapper by lazy {
            ContextPropagatorsWrapper(ContextPropagators.noop())
        }
    }

    actual val textMapPropagator: TextMapPropagatorWrapper
        get() = TextMapPropagatorWrapper(wrappedObject.textMapPropagator)
}
