package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes

actual interface ObservableLongMeasurement : ObservableMeasurement {

    actual fun record(value: Long)
    actual fun record(value: Long, attributes: Attributes)
}
