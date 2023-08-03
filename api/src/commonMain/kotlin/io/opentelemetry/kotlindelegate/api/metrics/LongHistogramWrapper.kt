package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper

expect class LongHistogramWrapper : IWrapper {

    fun record(value: Long)
    fun record(value: Long, attributesWrapper: AttributesWrapper)
    fun record(value: Long, attributesWrapper: AttributesWrapper, contextWrapper: ContextWrapper)
}
