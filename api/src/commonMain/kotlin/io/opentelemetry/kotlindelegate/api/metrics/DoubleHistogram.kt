package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

expect interface DoubleHistogram {
    fun record(value: Double)
    fun record(value: Double, attributes: Attributes)
    fun record(value: Double, attributes: Attributes, context: Context)
}
