package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.api.common.asJsAttributes
import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.Attributes as JsAttributes
import io.opentelemetry.kotlindelegate.js.Counter

internal class CounterWrapper(
    val jsCounter: Counter<JsAttributes>
): LongCounter, DoubleCounter {

    override fun add(value: Double) {
        jsCounter.add(value)
    }

    override fun add(value: Double, attributes: Attributes) {
        jsCounter.add(value, attributes.asJsAttributes())
    }

    override fun add(value: Double, attributes: Attributes, context: Context) {
        jsCounter.add(value, attributes.asJsAttributes(), context.asJsContext())
    }


    override fun add(value: Long) {
        jsCounter.add(value)
    }

    override fun add(value: Long, attributes: Attributes) {
        jsCounter.add(value, attributes.asJsAttributes())
    }

    override fun add(value: Long, attributes: Attributes, context: Context) {
        jsCounter.add(value, attributes.asJsAttributes(), context.asJsContext())
    }
}

fun Counter<JsAttributes>.asCommonLongCounter(): LongCounter = CounterWrapper(this)
fun Counter<JsAttributes>.asCommonDoubleCounter(): DoubleCounter = CounterWrapper(this)
