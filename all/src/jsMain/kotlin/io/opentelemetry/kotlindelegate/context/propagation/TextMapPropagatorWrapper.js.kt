package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.context.ContextWrapper

actual class TextMapPropagatorWrapper : IWrapper {
    actual companion object {

        actual fun composite(propagators: List<TextMapPropagatorWrapper>): TextMapPropagatorWrapper {
            TODO("Not yet implemented")
        }

        actual val Noop: TextMapPropagatorWrapper
            get() = TODO("Not yet implemented")
    }

    actual fun fields(): Collection<String> {
        TODO("Not yet implemented")
    }

    actual fun <C:Any> inject(
        context: ContextWrapper,
        carrier: C?,
        setter: TextMapSetterWrapper<C>,
    ) {
        TODO()
    }

    actual fun <C:Any> extract(
        context: ContextWrapper,
        carrier: C?,
        getter: TextMapGetterWrapper<C>,
    ): ContextWrapper {
        TODO("Not yet implemented")
    }
}
