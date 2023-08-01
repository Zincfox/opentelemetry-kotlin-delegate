package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.ObservableMeasurement
import io.opentelemetry.kotlindelegate.utils.IWrapper

actual interface ObservableMeasurementWrapper : IWrapper {

    override val wrappedObject: ObservableMeasurement
}
