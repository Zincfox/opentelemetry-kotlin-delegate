package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.java.Consumer

expect interface LongGaugeBuilder {
    fun setDescription(description: String): LongGaugeBuilder
    fun setUnit(unit: String): LongGaugeBuilder
    fun buildWithCallback(callback: Consumer<ObservableLongMeasurement>): ObservableLongGauge
    open fun buildObserver(): ObservableLongMeasurement
}
