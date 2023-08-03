package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributeKeyWrapper
import io.opentelemetry.kotlindelegate.api.common.AttributesWrapper
import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.utils.runInSpanDefaultOnError
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
    onError: (SpanWrapper, Throwable) -> T = ::runInSpanDefaultOnError,
    block: () -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        callsInPlace(onError, InvocationKind.AT_MOST_ONCE)
    }
    val wrapper = startSpan()
    return try {
        block()
    } catch (ex: Throwable) {
        onError(wrapper, ex)
    } finally {
        wrapper.end()
    }
}
