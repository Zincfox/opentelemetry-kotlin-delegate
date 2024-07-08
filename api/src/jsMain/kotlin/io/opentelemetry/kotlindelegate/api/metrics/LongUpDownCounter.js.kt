package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

actual interface LongUpDownCounter {

    actual fun add(value: Long)
    actual fun add(value: Long, attributes: Attributes)
    actual fun add(
        value: Long,
        attributes: Attributes,
        context: Context,
    )
}
