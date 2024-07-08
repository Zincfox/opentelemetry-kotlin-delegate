package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.Meter
import io.opentelemetry.kotlindelegate.js.MetricAdvice
import io.opentelemetry.kotlindelegate.js.MetricOptions
import io.opentelemetry.kotlindelegate.js.ValueType
import io.opentelemetry.kotlindelegate.js.Meter as JsMeter

abstract class MetricBuilderBase<THIS : MetricBuilderBase<THIS>>(
    protected val meter: JsMeter,
    protected val name: String,
    protected val valueType: ValueType,
) {

    protected var description: String? = null
    fun setDescription(description: String): THIS {
        this.description = description
        return this.unsafeCast<THIS>()
    }

    protected var unit: String? = null
    fun setUnit(unit: String): THIS {
        this.unit = unit
        return this.unsafeCast<THIS>()
    }

    protected open fun options(): MetricOptions {
        return js("{}").unsafeCast<MetricOptions>().also {
            if (description != null) it.description = description
            if (unit != null) it.unit = unit
            it.valueType = valueType
        }
    }

    open fun copyFrom(other: MetricBuilderBase<*>): THIS {
        this.description = other.description
        this.unit = other.unit
        return this.unsafeCast<THIS>()
    }

    abstract class HistogramBuilderBase<THIS : HistogramBuilderBase<THIS>>(
        meter: Meter,
        name: String,
        valueType: ValueType,
    ) : MetricBuilderBase<THIS>(
        meter, name, valueType
    ) {

        protected var explicitBucketBoundariesAdvice: Array<Number>? = null
        fun setExplicitBucketBoundariesAdviceLong(bucketBoundaries: List<Long>): THIS {
            explicitBucketBoundariesAdvice = bucketBoundaries.toTypedArray()
            return this.unsafeCast<THIS>()
        }
        fun setExplicitBucketBoundariesAdviceDouble(bucketBoundaries: List<Double>): THIS {
            explicitBucketBoundariesAdvice = bucketBoundaries.toTypedArray()
            return this.unsafeCast<THIS>()
        }

        override fun copyFrom(other: MetricBuilderBase<*>): THIS {
            return super.copyFrom(other).also {
                if(other is HistogramBuilderBase<*>) {
                    it.explicitBucketBoundariesAdvice = other.explicitBucketBoundariesAdvice
                }
            }
        }

        override fun options(): MetricOptions {
            return super.options().also {
                if (explicitBucketBoundariesAdvice != null) {
                    it.advice = (it.advice ?: js("{}").unsafeCast<MetricAdvice>()).also { advice ->
                        advice.explicitBucketBoundaries = explicitBucketBoundariesAdvice
                    }
                }
            }
        }
    }
}
