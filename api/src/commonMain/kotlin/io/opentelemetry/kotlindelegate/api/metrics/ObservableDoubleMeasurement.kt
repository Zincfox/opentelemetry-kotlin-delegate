package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.metrics.ObservableMeasurement

expect interface ObservableDoubleMeasurement : ObservableMeasurement {
    fun record(value: Double)
    fun record(value: Double, attributes: Attributes)
}
