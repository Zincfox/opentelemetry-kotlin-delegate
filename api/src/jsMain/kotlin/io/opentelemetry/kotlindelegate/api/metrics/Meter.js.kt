package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes as JsAttributes
import io.opentelemetry.kotlindelegate.js.BatchObservableCallback as JsBatchObservableCallback
import io.opentelemetry.kotlindelegate.js.Observable as JsObservable
import io.opentelemetry.kotlindelegate.js.Meter as JsMeter
import io.opentelemetry.kotlindelegate.utils.java.Runnable

actual interface Meter {

    actual fun counterBuilder(name: String): LongCounterBuilder
    actual fun upDownCounterBuilder(name: String): LongUpDownCounterBuilder
    actual fun histogramBuilder(name: String): DoubleHistogramBuilder
    actual fun gaugeBuilder(name: String): DoubleGaugeBuilder
    actual fun batchCallback(
        callback: Runnable,
        observableMeasurement: ObservableMeasurement,
        vararg additionalMeasurement: ObservableMeasurement,
    ): BatchCallback {
        return BatchCallbackImpl {}
    }
}

internal class MeterWrapper(val meter: JsMeter) : Meter {

    override fun counterBuilder(name: String): LongCounterBuilder {
        return LongCounterBuilderImpl(meter, name)
    }

    override fun upDownCounterBuilder(name: String): LongUpDownCounterBuilder {
        return LongUpDownCounterBuilderImpl(meter, name)
    }

    override fun histogramBuilder(name: String): DoubleHistogramBuilder {
        return DoubleHistogramBuilderImpl(meter, name)
    }

    override fun gaugeBuilder(name: String): DoubleGaugeBuilder {
        return DoubleGaugeBuilderImpl(meter, name)
    }

    override fun batchCallback(
        callback: Runnable,
        observableMeasurement: ObservableMeasurement,
        vararg additionalMeasurement: ObservableMeasurement,
    ): BatchCallback {
        val wrappers: List<ObservableMeasurementBatchWrapper> =
            (listOf(observableMeasurement) + additionalMeasurement.toList()).map {
                (it as ObservableMeasurementBatchWrapper)
            }
        val wrappedObservables: Array<JsObservable<JsAttributes>> = wrappers.map { it.observable }.toTypedArray()
        val jsCallback: JsBatchObservableCallback<JsAttributes> = { obsResult ->
            var wrappersAssigned: Int = -1
            try {
                wrappers.forEach { wrapper ->
                    wrapper.observableResult = obsResult
                    wrappersAssigned++
                }
                callback.run()
            } finally {
                for (i in 0..wrappersAssigned)
                    wrappers[i].observableResult = null
            }
            null
        }
        meter.addBatchObservableCallback(
            jsCallback,
            wrappedObservables
        )
        return BatchCallbackImpl {
            meter.removeBatchObservableCallback(jsCallback, wrappedObservables)
        }
    }
}
