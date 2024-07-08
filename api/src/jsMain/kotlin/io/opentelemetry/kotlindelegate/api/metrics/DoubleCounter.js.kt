package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

actual interface DoubleCounter {

    actual fun add(value: Double)
    actual fun add(value: Double, attributes: Attributes)
    actual fun add(
        value: Double,
        attributes: Attributes,
        context: Context,
    )
}
