package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.metrics.ObservableMeasurement

expect interface ObservableLongMeasurement : ObservableMeasurement {
    fun record(value: Long)
    fun record(value: Long, attributes: Attributes)
}
