package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper


expect class TextMapPropagatorWrapper : IWrapper {
    companion object {

        fun composite(propagators: List<TextMapPropagatorWrapper>): TextMapPropagatorWrapper
        val Noop: TextMapPropagatorWrapper
    }

    fun fields(): Collection<String>
    fun <C:Any> inject(context: ContextWrapper, carrier: C?, setter: TextMapSetterWrapper<C>)
    fun <C:Any> extract(context: ContextWrapper, carrier: C?, getter: TextMapGetterWrapper<C>): ContextWrapper
}

fun TextMapPropagatorWrapper.Companion.composite(vararg propagators: TextMapPropagatorWrapper): TextMapPropagatorWrapper =
    TextMapPropagatorWrapper.composite(propagators.toList())
