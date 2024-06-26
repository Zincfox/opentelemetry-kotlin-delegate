package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.java.Consumer

expect interface DoubleGaugeBuilder {
    fun setDescription(description: String): DoubleGaugeBuilder
    fun setUnit(unit: String): DoubleGaugeBuilder
    fun ofLongs(): LongGaugeBuilder
    fun buildWithCallback(callback: Consumer<ObservableDoubleMeasurement>): ObservableDoubleGauge
    open fun buildObserver(): ObservableDoubleMeasurement
}
