package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.context.Context

actual interface LongHistogram {

    actual fun record(value: Long)
    actual fun record(value: Long, attributes: Attributes)
    actual fun record(
        value: Long,
        attributes: Attributes,
        context: Context,
    )
}
