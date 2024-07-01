package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.ObservableCallback
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface DoubleCounterBuilder {

    actual fun setDescription(description: String): DoubleCounterBuilder
    actual fun setUnit(unit: String): DoubleCounterBuilder
    actual fun build(): DoubleCounter
    actual fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleCounter
    actual fun buildObserver(): ObservableDoubleMeasurement {
        throw NotImplementedError()
    }
}

internal class DoubleCounterBuilderImpl(meter: Meter, name: String) : MetricBuilderBase<DoubleCounterBuilderImpl>(
    meter,
    name,
    ValueType.DOUBLE
), DoubleCounterBuilder {

    override fun build(): DoubleCounter {
        return CounterWrapper(meter.createCounter(name, options()))
    }

    override fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleCounter {
        val counter = meter.createObservableCounter<Attributes>(name, options())
        val wrappedCallback: ObservableCallback<Attributes> = {
            callback.accept(ObservableMeasurementDelegate(it))
            null
        }
        counter.addCallback(wrappedCallback)
        return object : ObservableDoubleCounter {
            override fun close() {
                counter.removeCallback(wrappedCallback)
            }
        }
    }

    override fun buildObserver(): ObservableDoubleMeasurement {
        return ObservableMeasurementBatchWrapper(meter.createObservableCounter(name, options()))
    }
}
