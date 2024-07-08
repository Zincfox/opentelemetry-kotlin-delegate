package io.opentelemetry.kotlindelegate.api.metrics


expect interface DoubleHistogramBuilder {
    fun setDescription(description: String): DoubleHistogramBuilder
    fun setUnit(unit: String): DoubleHistogramBuilder
    open fun setExplicitBucketBoundariesAdvice(bucketBoundaries: List<Double>): DoubleHistogramBuilder
    fun ofLongs(): LongHistogramBuilder
    fun build(): DoubleHistogram
}
