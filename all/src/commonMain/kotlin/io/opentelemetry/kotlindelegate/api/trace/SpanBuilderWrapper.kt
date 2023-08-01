package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributeKeyWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.time.DurationUnit

expect class SpanBuilderWrapper : IWrapper {

    fun setParent(context: ContextWrapper): SpanBuilderWrapper
    fun setNoParent(): SpanBuilderWrapper
    fun addLink(spanContext: SpanContextWrapper): SpanBuilderWrapper
    fun addLink(spanContext: SpanContextWrapper, attributes: AttributesWrapper): SpanBuilderWrapper
    fun setAttribute(key: String, value: String): SpanBuilderWrapper
    fun setAttribute(key: String, value: Long): SpanBuilderWrapper
    fun setAttribute(key: String, value: Double): SpanBuilderWrapper
    fun setAttribute(key: String, value: Boolean): SpanBuilderWrapper
    fun <T:Any> setAttribute(key: AttributeKeyWrapper<T>, value: T): SpanBuilderWrapper
    fun setAllAttributes(attributes: AttributesWrapper): SpanBuilderWrapper
    fun setSpanKind(spanKind: SpanKindWrapper): SpanBuilderWrapper
    fun setStartTimestamp(startTimestamp: Long, unit: DurationUnit): SpanBuilderWrapper
    fun startSpan(): SpanWrapper
}

@OptIn(ExperimentalContracts::class)
inline fun <T> SpanBuilderWrapper.runInside(
    onException: (SpanWrapper, Throwable) -> Unit =
        { span, ex ->
            span.recordException(ex)
            span.setStatus(StatusCodeWrapper.ERROR)
        },
    block: () -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val wrapper = startSpan()
    val result: T
    try {
        result = block()
    } catch (ex: Throwable) {
        onException(wrapper, ex)
        throw ex
    } finally {
        wrapper.end()
    }
    return result
}
