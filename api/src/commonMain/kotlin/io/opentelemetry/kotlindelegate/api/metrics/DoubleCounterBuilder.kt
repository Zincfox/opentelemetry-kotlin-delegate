package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.java.Consumer

expect interface DoubleCounterBuilder {
    fun setDescription(description: String): DoubleCounterBuilder
    fun setUnit(unit: String): DoubleCounterBuilder
    fun build(): DoubleCounter
    fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleCounter
    open fun buildObserver(): ObservableDoubleMeasurement
}
