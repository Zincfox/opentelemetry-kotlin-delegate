package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.api.common.asJsAttributes
import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.Attributes as JsAttributes
import io.opentelemetry.kotlindelegate.js.Histogram

internal class HistogramWrapper(
    val jsCounter: Histogram<JsAttributes>
): LongHistogram, DoubleHistogram {

    override fun record(value: Double) {
        jsCounter.record(value)
    }

    override fun record(value: Double, attributes: Attributes) {
        jsCounter.record(value, attributes.asJsAttributes())
    }

    override fun record(value: Double, attributes: Attributes, context: Context) {
        jsCounter.record(value, attributes.asJsAttributes(), context.asJsContext())
    }


    override fun record(value: Long) {
        jsCounter.record(value)
    }

    override fun record(value: Long, attributes: Attributes) {
        jsCounter.record(value, attributes.asJsAttributes())
    }

    override fun record(value: Long, attributes: Attributes, context: Context) {
        jsCounter.record(value, attributes.asJsAttributes(), context.asJsContext())
    }
}
