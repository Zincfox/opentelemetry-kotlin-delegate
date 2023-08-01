package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

expect class LongCounterWrapper : IWrapper {

    fun add(value: Long)
    fun add(value: Long, attributes: AttributesWrapper)
    fun add(value: Long, attributes: AttributesWrapper, context: ContextWrapper)
}
