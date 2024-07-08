package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.java.Consumer

expect interface DoubleUpDownCounterBuilder {
    fun setDescription(description: String): DoubleUpDownCounterBuilder
    fun setUnit(unit: String): DoubleUpDownCounterBuilder
    fun build(): DoubleUpDownCounter
    fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleUpDownCounter
    open fun buildObserver(): ObservableDoubleMeasurement
}
