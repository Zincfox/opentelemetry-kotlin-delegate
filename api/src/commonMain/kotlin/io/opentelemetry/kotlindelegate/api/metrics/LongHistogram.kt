package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

expect interface LongHistogram {
    fun record(value: Long)
    fun record(value: Long, attributes: Attributes)
    fun record(value: Long, attributes: Attributes, context: Context)
}
