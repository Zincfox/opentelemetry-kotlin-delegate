package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

actual class TracerWrapper(
    override val wrappedObject: Tracer
) : WrapperBase<Tracer>(),IWrapper {

    actual fun spanBuilder(spanName: String): SpanBuilderWrapper {
        return wrappedObject.spanBuilder(spanName).let(::SpanBuilderWrapper)
    }

    @OptIn(ExperimentalContracts::class)
    actual inline fun <T> runInSpan(
        spanName: String,
        onException: (SpanWrapper, Throwable) -> Unit,
        block: () -> T,
    ): T {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }
        val span = wrappedObject.spanBuilder(spanName).startSpan()
        val result: T
        try {
            result = block()
        } catch (ex: Throwable) {
            onException(SpanWrapper(span), ex)
            throw ex
        } finally {
            span.end()
        }
        return result
    }
}
