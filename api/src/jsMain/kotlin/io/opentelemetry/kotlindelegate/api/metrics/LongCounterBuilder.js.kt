package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.ObservableCallback
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.js.Meter as JsMeter
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface LongCounterBuilder {

    actual fun setDescription(description: String): LongCounterBuilder
    actual fun setUnit(unit: String): LongCounterBuilder
    actual fun ofDoubles(): DoubleCounterBuilder
    actual fun build(): LongCounter
    actual fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongCounter
    actual fun buildObserver(): ObservableLongMeasurement {
        throw NotImplementedError()
    }
}

internal class LongCounterBuilderImpl(meter: JsMeter, name: String) : MetricBuilderBase<LongCounterBuilderImpl>(
    meter,
    name,
    ValueType.INT
), LongCounterBuilder {

    override fun ofDoubles(): DoubleCounterBuilder {
        return DoubleCounterBuilderImpl(meter, name).copyFrom(this)
    }

    override fun build(): LongCounter {
        return CounterWrapper(meter.createCounter(name, options()))
    }

    override fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongCounter {
        val counter = meter.createObservableCounter<Attributes>(name, options())
        val wrappedCallback: ObservableCallback<Attributes> = {
            callback.accept(ObservableMeasurementDelegate(it))
            null
        }
        counter.addCallback(wrappedCallback)
        return object : ObservableLongCounter {
            override fun close() {
                counter.removeCallback(wrappedCallback)
            }
        }
    }

    override fun buildObserver(): ObservableLongMeasurement {
        return ObservableMeasurementBatchWrapper(meter.createObservableCounter(name, options()))
    }
}
