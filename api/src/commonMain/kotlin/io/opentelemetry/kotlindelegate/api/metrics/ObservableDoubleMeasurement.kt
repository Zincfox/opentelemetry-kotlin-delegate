package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes

expect interface ObservableDoubleMeasurement : ObservableMeasurement {
    fun record(value: Double)
    fun record(value: Double, attributes: Attributes)
}
