package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.ObservableCallback
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface LongUpDownCounterBuilder {

    actual fun setDescription(description: String): LongUpDownCounterBuilder
    actual fun setUnit(unit: String): LongUpDownCounterBuilder
    actual fun ofDoubles(): DoubleUpDownCounterBuilder
    actual fun build(): LongUpDownCounter
    actual fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongUpDownCounter
    actual fun buildObserver(): ObservableLongMeasurement {
        throw NotImplementedError()
    }
}

internal class LongUpDownCounterBuilderImpl(meter: Meter, name: String) : MetricBuilderBase<LongUpDownCounterBuilderImpl>(
    meter,
    name,
    ValueType.INT
), LongUpDownCounterBuilder {

    override fun build(): LongUpDownCounter {
        return UpDownCounterWrapper(meter.createUpDownCounter(name, options()))
    }

    override fun ofDoubles(): DoubleUpDownCounterBuilder {
        return DoubleUpDownCounterBuilderImpl(meter, name).copyFrom(this)
    }

    override fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongUpDownCounter {
        val counter = meter.createObservableUpDownCounter<Attributes>(name, options())
        val wrappedCallback: ObservableCallback<Attributes> = {
            callback.accept(ObservableMeasurementDelegate(it))
            null
        }
        counter.addCallback(wrappedCallback)
        return object : ObservableLongUpDownCounter {
            override fun close() {
                counter.removeCallback(wrappedCallback)
            }
        }
    }

    override fun buildObserver(): ObservableLongMeasurement {
        return ObservableMeasurementBatchWrapper(meter.createObservableUpDownCounter(name, options()))
    }
}
