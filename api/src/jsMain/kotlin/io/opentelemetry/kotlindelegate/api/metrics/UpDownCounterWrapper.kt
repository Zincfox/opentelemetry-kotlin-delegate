package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.asJsAttributes
import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.Attributes
import io.opentelemetry.kotlindelegate.js.UpDownCounter

internal class UpDownCounterWrapper(
    val jsCounter: UpDownCounter<Attributes>
): LongUpDownCounter, DoubleUpDownCounter {

    override fun add(value: Double) {
        jsCounter.add(value)
    }

    override fun add(value: Double, attributes: io.opentelemetry.kotlindelegate.api.common.Attributes) {
        jsCounter.add(value, attributes.asJsAttributes())
    }

    override fun add(value: Double, attributes: io.opentelemetry.kotlindelegate.api.common.Attributes, context: Context) {
        jsCounter.add(value, attributes.asJsAttributes(), context.asJsContext())
    }


    override fun add(value: Long) {
        jsCounter.add(value)
    }

    override fun add(value: Long, attributes: io.opentelemetry.kotlindelegate.api.common.Attributes) {
        jsCounter.add(value, attributes.asJsAttributes())
    }

    override fun add(value: Long, attributes: io.opentelemetry.kotlindelegate.api.common.Attributes, context: Context) {
        jsCounter.add(value, attributes.asJsAttributes(), context.asJsContext())
    }
}
