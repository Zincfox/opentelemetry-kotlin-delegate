package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes

expect interface ObservableLongMeasurement : ObservableMeasurement {
    fun record(value: Long)
    fun record(value: Long, attributes: Attributes)
}
