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
        onError: (SpanWrapper, Throwable) -> T,
        block: () -> T,
    ): T {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            callsInPlace(onError, InvocationKind.AT_MOST_ONCE)
        }
        val started = wrappedObject.spanBuilder(spanName).startSpan()
        return try {
            block()
        } catch (ex: Throwable) {
            onError(started.let(::SpanWrapper), ex)
        } finally {
            started.end()
        }
    }
}
