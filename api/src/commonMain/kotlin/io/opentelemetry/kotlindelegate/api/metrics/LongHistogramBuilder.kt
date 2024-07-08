package io.opentelemetry.kotlindelegate.api.metrics


expect interface LongHistogramBuilder {
    fun setDescription(description: String): LongHistogramBuilder
    fun setUnit(unit: String): LongHistogramBuilder
    open fun setExplicitBucketBoundariesAdvice(bucketBoundaries: List<Long>): LongHistogramBuilder
    fun build(): LongHistogram
}
