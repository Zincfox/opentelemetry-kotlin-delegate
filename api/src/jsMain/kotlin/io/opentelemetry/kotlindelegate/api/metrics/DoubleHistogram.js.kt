package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

actual interface DoubleHistogram {

    actual fun record(value: Double)
    actual fun record(value: Double, attributes: Attributes)
    actual fun record(
        value: Double,
        attributes: Attributes,
        context: Context,
    )
}
