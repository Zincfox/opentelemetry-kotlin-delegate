package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.*
import java.util.function.Consumer

expect interface LongCounterBuilder {
    fun setDescription(description: String): LongCounterBuilder
    fun setUnit(unit: String): LongCounterBuilder
    fun ofDoubles(): DoubleCounterBuilder
    fun build(): LongCounter
    fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongCounter
    open fun buildObserver(): ObservableLongMeasurement
}
