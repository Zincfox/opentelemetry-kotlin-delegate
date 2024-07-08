package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.ObservableCallback
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface DoubleUpDownCounterBuilder {

    actual fun setDescription(description: String): DoubleUpDownCounterBuilder
    actual fun setUnit(unit: String): DoubleUpDownCounterBuilder
    actual fun build(): DoubleUpDownCounter
    actual fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleUpDownCounter
    actual fun buildObserver(): ObservableDoubleMeasurement {
        throw NotImplementedError()
    }
}

internal class DoubleUpDownCounterBuilderImpl(meter: Meter, name: String) : MetricBuilderBase<DoubleUpDownCounterBuilderImpl>(
    meter,
    name,
    ValueType.DOUBLE
), DoubleUpDownCounterBuilder {

    override fun build(): DoubleUpDownCounter {
        return UpDownCounterWrapper(meter.createUpDownCounter(name, options()))
    }

    override fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleUpDownCounter {
        val counter = meter.createObservableUpDownCounter<Attributes>(name, options())
        val wrappedCallback: ObservableCallback<Attributes> = {
            callback.accept(ObservableMeasurementDelegate(it))
            null
        }
        counter.addCallback(wrappedCallback)
        return object : ObservableDoubleUpDownCounter {
            override fun close() {
                counter.removeCallback(wrappedCallback)
            }
        }
    }

    override fun buildObserver(): ObservableDoubleMeasurement {
        return ObservableMeasurementBatchWrapper(meter.createObservableUpDownCounter(name, options()))
    }
}
