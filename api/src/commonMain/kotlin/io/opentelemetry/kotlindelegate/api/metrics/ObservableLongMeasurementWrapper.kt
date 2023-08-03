package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper

expect class ObservableLongMeasurementWrapper : ObservableMeasurementWrapper {

    fun record(value: Long)
    fun record(value: Long, attributes: AttributesWrapper)
}
