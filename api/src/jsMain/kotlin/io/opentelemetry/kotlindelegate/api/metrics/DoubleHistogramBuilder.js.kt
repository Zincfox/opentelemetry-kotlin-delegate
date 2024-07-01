package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.ValueType

actual interface DoubleHistogramBuilder {

    actual fun setDescription(description: String): DoubleHistogramBuilder
    actual fun setUnit(unit: String): DoubleHistogramBuilder
    actual fun setExplicitBucketBoundariesAdvice(bucketBoundaries: List<Double>): DoubleHistogramBuilder {
        return this
    }

    actual fun ofLongs(): LongHistogramBuilder
    actual fun build(): DoubleHistogram
}

internal class DoubleHistogramBuilderImpl(meter: Meter, name: String) : MetricBuilderBase.HistogramBuilderBase<DoubleHistogramBuilderImpl>(
    meter,
    name,
    ValueType.DOUBLE
), DoubleHistogramBuilder {

    override fun setExplicitBucketBoundariesAdvice(bucketBoundaries: List<Double>): DoubleHistogramBuilderImpl {
        return setExplicitBucketBoundariesAdviceDouble(bucketBoundaries)
    }

    override fun ofLongs(): LongHistogramBuilder {
        return LongHistogramBuilderImpl(meter, name).copyFrom(this)
    }

    override fun build(): DoubleHistogram {
        return HistogramWrapper(meter.createHistogram(name, options()))
    }
}
