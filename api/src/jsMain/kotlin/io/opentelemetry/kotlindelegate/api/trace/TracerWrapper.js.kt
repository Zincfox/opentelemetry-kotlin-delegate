package io.opentelemetry.kotlindelegate.api.trace

actual class TracerWrapper : IWrapper {

    actual fun spanBuilder(spanName: String): SpanBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual inline fun <T> runInSpan(
        spanName: String,
        onException: (SpanWrapper, Throwable) -> Unit,
        block: () -> T,
    ): T {
        TODO("Not yet implemented")
    }
}
