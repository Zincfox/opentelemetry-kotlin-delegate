@file:Suppress("ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING")
//suppressed because kotlin wants the signature of expects in interfaces to match the specific signature of the actuals,
// ignoring typealiases.

package io.opentelemetry.kotlindelegate.api.metrics


actual typealias MeterProvider = io.opentelemetry.api.metrics.MeterProvider
actual typealias MeterBuilder = io.opentelemetry.api.metrics.MeterBuilder
actual typealias Meter = io.opentelemetry.api.metrics.Meter
actual typealias BatchCallback = io.opentelemetry.api.metrics.BatchCallback
actual typealias ObservableMeasurement = io.opentelemetry.api.metrics.ObservableMeasurement
actual typealias ObservableDoubleMeasurement = io.opentelemetry.api.metrics.ObservableDoubleMeasurement
actual typealias ObservableLongMeasurement = io.opentelemetry.api.metrics.ObservableLongMeasurement
actual typealias LongCounterBuilder = io.opentelemetry.api.metrics.LongCounterBuilder
actual typealias DoubleCounterBuilder = io.opentelemetry.api.metrics.DoubleCounterBuilder
actual typealias LongCounter = io.opentelemetry.api.metrics.LongCounter
actual typealias DoubleCounter = io.opentelemetry.api.metrics.DoubleCounter
actual typealias LongUpDownCounterBuilder = io.opentelemetry.api.metrics.LongUpDownCounterBuilder
actual typealias DoubleUpDownCounterBuilder = io.opentelemetry.api.metrics.DoubleUpDownCounterBuilder
actual typealias LongUpDownCounter = io.opentelemetry.api.metrics.LongUpDownCounter
actual typealias DoubleUpDownCounter = io.opentelemetry.api.metrics.DoubleUpDownCounter
actual typealias ObservableLongCounter = io.opentelemetry.api.metrics.ObservableLongCounter
actual typealias ObservableDoubleCounter = io.opentelemetry.api.metrics.ObservableDoubleCounter
actual typealias ObservableLongUpDownCounter = io.opentelemetry.api.metrics.ObservableLongUpDownCounter
actual typealias ObservableDoubleUpDownCounter = io.opentelemetry.api.metrics.ObservableDoubleUpDownCounter
actual typealias LongHistogram = io.opentelemetry.api.metrics.LongHistogram
actual typealias DoubleHistogram = io.opentelemetry.api.metrics.DoubleHistogram
actual typealias LongHistogramBuilder = io.opentelemetry.api.metrics.LongHistogramBuilder
actual typealias DoubleHistogramBuilder = io.opentelemetry.api.metrics.DoubleHistogramBuilder
actual typealias ObservableLongGauge = io.opentelemetry.api.metrics.ObservableLongGauge
actual typealias ObservableDoubleGauge = io.opentelemetry.api.metrics.ObservableDoubleGauge
actual typealias DoubleGaugeBuilder = io.opentelemetry.api.metrics.DoubleGaugeBuilder
actual typealias LongGaugeBuilder = io.opentelemetry.api.metrics.LongGaugeBuilder
