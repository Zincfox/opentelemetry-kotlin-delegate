package io.opentelemetry.kotlindelegate.api.metrics


expect interface Meter {
    fun counterBuilder(name: String): LongCounterBuilder
    fun upDownCounterBuilder(name: String): LongUpDownCounterBuilder
    fun histogramBuilder(name: String): DoubleHistogramBuilder
    fun gaugeBuilder(name: String): DoubleGaugeBuilder
    open fun batchCallback(callback: Runnable, observableMeasurement: ObservableMeasurement, vararg additionalMeasurement: ObservableMeasurement): BatchCallback
}
