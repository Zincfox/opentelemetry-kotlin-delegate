package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.api.common.asJsAttributes
import io.opentelemetry.kotlindelegate.js.Attributes as JsAttributes
import io.opentelemetry.kotlindelegate.js.BatchObservableResult
import io.opentelemetry.kotlindelegate.js.Observable
import io.opentelemetry.kotlindelegate.js.ObservableResult

actual interface ObservableMeasurement

class ObservableMeasurementBatchWrapper(
    val observable: Observable<JsAttributes>,
) : ObservableLongMeasurement, ObservableDoubleMeasurement {

    var observableResult: BatchObservableResult<JsAttributes>? = null
        set(value) {
            val storedField = field
            if (storedField != null && value != null) {
                throw IllegalStateException("Assigning a new BatchObservableResult is forbidden while a previous one is still referenced ($storedField would be discarded for $value)")
            }
            field = value
        }

    override fun record(value: Long) {
        val observableResult = this.observableResult ?: return
        observableResult.observe(observableResult, observable, value)
    }

    override fun record(value: Double) {
        val observableResult = this.observableResult ?: return
        observableResult.observe(observableResult, observable, value)
    }

    override fun record(value: Double, attributes: Attributes) {
        val observableResult = this.observableResult ?: return
        observableResult.observe(observableResult, observable, value, attributes.asJsAttributes())
    }

    override fun record(value: Long, attributes: Attributes) {
        val observableResult = this.observableResult ?: return
        observableResult.observe(observableResult, observable, value, attributes.asJsAttributes())
    }
}

internal class ObservableMeasurementDelegate(val recorder: ObservableResult<JsAttributes>) : ObservableLongMeasurement,
        ObservableDoubleMeasurement {

    override fun record(value: Long) {
        recorder.observe(recorder, value)
    }

    override fun record(value: Double) {
        recorder.observe(recorder, value)
    }

    override fun record(value: Long, attributes: Attributes) {
        recorder.observe(recorder, value, attributes.asJsAttributes())
    }

    override fun record(value: Double, attributes: Attributes) {
        recorder.observe(recorder, value, attributes.asJsAttributes())
    }
}
