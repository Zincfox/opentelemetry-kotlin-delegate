package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.context.propagation.TextMapPropagator
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.context.ContextWrapper

actual class TextMapPropagatorWrapper(
    override val wrappedObject: TextMapPropagator,
) : WrapperBase<TextMapPropagator>(), IWrapper {

    actual companion object {

        actual fun composite(propagators: List<TextMapPropagatorWrapper>): TextMapPropagatorWrapper {
            return TextMapPropagatorWrapper(TextMapPropagator.composite(propagators.map { it.wrappedObject }))
        }

        actual val Noop: TextMapPropagatorWrapper by lazy {
            TextMapPropagatorWrapper(TextMapPropagator.noop())
        }
    }

    actual fun fields(): Collection<String> = wrappedObject.fields()

    actual fun <C:Any> inject(
        context: ContextWrapper,
        carrier: C?,
        setter: TextMapSetterWrapper<C>,
    ) = wrappedObject.inject(context.wrappedObject, carrier, setter)

    actual fun <C:Any> extract(
        context: ContextWrapper,
        carrier: C?,
        getter: TextMapGetterWrapper<C>,
    ): ContextWrapper = ContextWrapper(wrappedObject.extract(context.wrappedObject, carrier, getter.wrappedObject))
}
