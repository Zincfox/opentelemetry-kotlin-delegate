package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.ObservableCallback
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface LongGaugeBuilder {

    actual fun setDescription(description: String): LongGaugeBuilder
    actual fun setUnit(unit: String): LongGaugeBuilder
    actual fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongGauge
    actual fun buildObserver(): ObservableLongMeasurement {
        throw NotImplementedError()
    }
}


internal class LongGaugeBuilderImpl(meter: Meter, name: String) : MetricBuilderBase<LongGaugeBuilderImpl>(
    meter,
    name,
    ValueType.INT
), LongGaugeBuilder {

    override fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongGauge {
        val counter = meter.createObservableGauge<Attributes>(name, options())
        val wrappedCallback: ObservableCallback<Attributes> = {
            callback.accept(ObservableMeasurementDelegate(it))
            null
        }
        counter.addCallback(wrappedCallback)
        return object : ObservableLongGauge {
            override fun close() {
                counter.removeCallback(wrappedCallback)
            }
        }
    }

    override fun buildObserver(): ObservableLongMeasurement {
        return ObservableMeasurementBatchWrapper(meter.createObservableGauge(name, options()))
    }
}
