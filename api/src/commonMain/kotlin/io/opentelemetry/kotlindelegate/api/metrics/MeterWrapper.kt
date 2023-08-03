package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class MeterWrapper : IWrapper {

    fun longCounterBuilder(name: String): LongCounterBuilderWrapper
    fun longUpDownCounterBuilder(name: String): LongUpDownCounterBuilderWrapper
    fun doubleHistogramBuilder(name: String): DoubleHistogramBuilderWrapper
    fun doubleGaugeBuilder(name: String): DoubleGaugeBuilderWrapper
    fun doubleCounterBuilder(name: String): DoubleCounterBuilderWrapper
    fun doubleUpDownCounterBuilder(name: String): DoubleUpDownCounterBuilderWrapper
    fun longHistogramBuilder(name: String): LongHistogramBuilderWrapper
    fun longGaugeBuilder(name: String): LongGaugeBuilderWrapper

    fun batchCallback(
        observableMeasurement: ObservableMeasurementWrapper,
        vararg additionalMeasurements: ObservableMeasurementWrapper,
        callback: () -> Unit,
    ): BatchCallbackWrapper
}


fun MeterWrapper.buildLongCounter(
    name: String,
    configuration: LongCounterBuilderWrapper.() -> Unit,
): LongCounterWrapper = longCounterBuilder(name).apply(configuration).build()

fun MeterWrapper.buildAsyncLongCounter(
    name: String,
    configuration: LongCounterBuilderWrapper.() -> Unit,
    callback: (ObservableLongMeasurementWrapper) -> Unit,
): ObservableLongCounterWrapper = longCounterBuilder(name).apply(configuration).buildWithCallback(callback)

fun MeterWrapper.buildLongUpDownCounter(
    name: String,
    configuration: LongUpDownCounterBuilderWrapper.() -> Unit,
): LongUpDownCounterWrapper = longUpDownCounterBuilder(name).apply(configuration).build()

fun MeterWrapper.buildAsyncLongUpDownCounter(
    name: String,
    configuration: LongUpDownCounterBuilderWrapper.() -> Unit,
    callback: (ObservableLongMeasurementWrapper) -> Unit,
): ObservableLongUpDownCounterWrapper = longUpDownCounterBuilder(name).apply(configuration).buildWithCallback(callback)

fun MeterWrapper.buildAsyncLongGauge(
    name: String,
    configuration: LongGaugeBuilderWrapper.() -> Unit,
    callback: (ObservableLongMeasurementWrapper) -> Unit,
): ObservableLongGaugeWrapper = longGaugeBuilder(name).apply(configuration).buildWithCallback(callback)

fun MeterWrapper.buildLongHistogram(
    name: String,
    configuration: LongHistogramBuilderWrapper.() -> Unit,
): LongHistogramWrapper = longHistogramBuilder(name).apply(configuration).build()

fun MeterWrapper.buildDoubleCounter(
    name: String,
    configuration: DoubleCounterBuilderWrapper.() -> Unit,
): DoubleCounterWrapper = doubleCounterBuilder(name).apply(configuration).build()

fun MeterWrapper.buildAsyncDoubleCounter(
    name: String,
    configuration: DoubleCounterBuilderWrapper.() -> Unit,
    callback: (ObservableDoubleMeasurementWrapper) -> Unit,
): ObservableDoubleCounterWrapper = doubleCounterBuilder(name).apply(configuration).buildWithCallback(callback)

fun MeterWrapper.buildDoubleUpDownCounter(
    name: String,
    configuration: DoubleUpDownCounterBuilderWrapper.() -> Unit,
): DoubleUpDownCounterWrapper = doubleUpDownCounterBuilder(name).apply(configuration).build()

fun MeterWrapper.buildAsyncDoubleUpDownCounter(
    name: String,
    configuration: DoubleUpDownCounterBuilderWrapper.() -> Unit,
    callback: (ObservableDoubleMeasurementWrapper) -> Unit,
): ObservableDoubleUpDownCounterWrapper =
    doubleUpDownCounterBuilder(name).apply(configuration).buildWithCallback(callback)

fun MeterWrapper.buildAsyncDoubleGauge(
    name: String,
    configuration: DoubleGaugeBuilderWrapper.() -> Unit,
    callback: (ObservableDoubleMeasurementWrapper) -> Unit,
): ObservableDoubleGaugeWrapper = doubleGaugeBuilder(name).apply(configuration).buildWithCallback(callback)

fun MeterWrapper.buildDoubleHistogram(
    name: String,
    configuration: DoubleHistogramBuilderWrapper.() -> Unit,
): DoubleHistogramWrapper = doubleHistogramBuilder(name).apply(configuration).build()
