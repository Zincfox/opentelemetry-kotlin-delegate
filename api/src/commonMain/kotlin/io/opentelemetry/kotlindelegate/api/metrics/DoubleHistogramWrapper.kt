package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

expect class DoubleHistogramWrapper : IWrapper {

    fun record(value: Double)
    fun record(value: Double, attributesWrapper: AttributesWrapper)
    fun record(value: Double, attributesWrapper: AttributesWrapper, contextWrapper: ContextWrapper)
}
