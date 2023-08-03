package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

expect class DoubleUpDownCounterWrapper : IWrapper {

    fun add(value: Double)
    fun add(value: Double, attributes: AttributesWrapper)
    fun add(value: Double, attributes: AttributesWrapper, contextWrapper: ContextWrapper)
}
