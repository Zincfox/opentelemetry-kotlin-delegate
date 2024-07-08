package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes

actual interface ObservableDoubleMeasurement : ObservableMeasurement {

    actual fun record(value: Double)
    actual fun record(value: Double, attributes: Attributes)
}
