package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.ObservableCallback
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface DoubleGaugeBuilder {

    actual fun setDescription(description: String): DoubleGaugeBuilder
    actual fun setUnit(unit: String): DoubleGaugeBuilder
    actual fun ofLongs(): LongGaugeBuilder
    actual fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleGauge
    actual fun buildObserver(): ObservableDoubleMeasurement {
        throw NotImplementedError()
    }
}


internal class DoubleGaugeBuilderImpl(meter: Meter, name: String) : MetricBuilderBase<DoubleGaugeBuilderImpl>(
    meter,
    name,
    ValueType.DOUBLE
), DoubleGaugeBuilder {

    override fun ofLongs(): LongGaugeBuilder {
        return LongGaugeBuilderImpl(meter, name).copyFrom(this)
    }

    override fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleGauge {
        val counter = meter.createObservableGauge<Attributes>(name, options())
        val wrappedCallback: ObservableCallback<Attributes> = {
            callback.accept(ObservableMeasurementDelegate(it))
            null
        }
        counter.addCallback(wrappedCallback)
        return object : ObservableDoubleGauge {
            override fun close() {
                counter.removeCallback(wrappedCallback)
            }
        }
    }

    override fun buildObserver(): ObservableDoubleMeasurement {
        return ObservableMeasurementBatchWrapper(meter.createObservableGauge(name, options()))
    }
}
