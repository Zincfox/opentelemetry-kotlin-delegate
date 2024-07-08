package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

expect interface DoubleUpDownCounter {
    fun add(value: Double)
    fun add(value: Double, attributes: Attributes)
    fun add(value: Double, attributes: Attributes, context: Context)
}
