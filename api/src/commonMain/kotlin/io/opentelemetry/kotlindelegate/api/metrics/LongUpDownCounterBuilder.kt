package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.java.Consumer

expect interface LongUpDownCounterBuilder {
    fun setDescription(description: String): LongUpDownCounterBuilder
    fun setUnit(unit: String): LongUpDownCounterBuilder
    fun ofDoubles(): DoubleUpDownCounterBuilder
    fun build(): LongUpDownCounter
    fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongUpDownCounter
    open fun buildObserver(): ObservableLongMeasurement
}
