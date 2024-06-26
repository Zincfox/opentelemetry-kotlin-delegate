package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

expect interface LongUpDownCounter {
    fun add(value: Long)
    fun add(value: Long, attributes: Attributes)
    fun add(value: Long, attributes: Attributes, context: Context)
}
