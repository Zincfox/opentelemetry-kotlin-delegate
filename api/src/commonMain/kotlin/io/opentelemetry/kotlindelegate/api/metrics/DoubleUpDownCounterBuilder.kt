package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.*
import java.util.function.Consumer

expect interface DoubleUpDownCounterBuilder {
    fun setDescription(description: String): DoubleUpDownCounterBuilder
    fun setUnit(unit: String): DoubleUpDownCounterBuilder
    fun build(): DoubleUpDownCounter
    fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleUpDownCounter
    open fun buildObserver(): ObservableDoubleMeasurement
}
