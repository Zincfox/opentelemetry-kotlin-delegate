package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class ContextPropagatorsWrapper : IWrapper {
    companion object {

        fun create(textPropagator: TextMapPropagatorWrapper): ContextPropagatorsWrapper
        val Noop: ContextPropagatorsWrapper
    }

    val textMapPropagator: TextMapPropagatorWrapper
}
