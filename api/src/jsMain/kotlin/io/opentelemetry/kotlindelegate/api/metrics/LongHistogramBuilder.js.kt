package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface LongHistogramBuilder {

    actual fun setDescription(description: String): LongHistogramBuilder
    actual fun setUnit(unit: String): LongHistogramBuilder
    actual fun setExplicitBucketBoundariesAdvice(bucketBoundaries: List<Long>): LongHistogramBuilder {
        return this
    }

    actual fun build(): LongHistogram
}

internal class LongHistogramBuilderImpl(meter: Meter, name: String) : MetricBuilderBase.HistogramBuilderBase<LongHistogramBuilderImpl>(
    meter,
    name,
    ValueType.INT
), LongHistogramBuilder {

    override fun setExplicitBucketBoundariesAdvice(bucketBoundaries: List<Long>): LongHistogramBuilderImpl {
        return setExplicitBucketBoundariesAdviceLong(bucketBoundaries)
    }

    override fun build(): LongHistogram {
        return HistogramWrapper(meter.createHistogram(name, options()))
    }
}
