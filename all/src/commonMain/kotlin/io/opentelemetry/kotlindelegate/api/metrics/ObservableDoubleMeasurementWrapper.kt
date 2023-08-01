package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper

expect class ObservableDoubleMeasurementWrapper : ObservableMeasurementWrapper {

    fun record(value: Double)
    fun record(value: Double, attributes: AttributesWrapper)
}
