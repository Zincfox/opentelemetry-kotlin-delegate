package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper

actual class ObservableDoubleMeasurementWrapper : ObservableMeasurementWrapper {

    actual fun record(value: Double) {
    }

    actual fun record(value: Double, attributes: AttributesWrapper) {
    }
}
