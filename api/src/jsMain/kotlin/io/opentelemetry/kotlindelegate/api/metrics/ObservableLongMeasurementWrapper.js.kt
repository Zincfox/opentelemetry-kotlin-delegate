package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper

actual class ObservableLongMeasurementWrapper : ObservableMeasurementWrapper {

    actual fun record(value: Long) {
    }

    actual fun record(value: Long, attributes: AttributesWrapper) {
    }
}
