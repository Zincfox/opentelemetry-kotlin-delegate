package io.opentelemetry.kotlindelegate.api.trace

actual class TraceStateWrapper : IWrapper, Map<String, String> {
    actual companion object {

        actual val Default: TraceStateWrapper
            get() = TODO("Not yet implemented")

        actual fun builder(): TraceStateBuilderWrapper {
            TODO("Not yet implemented")
        }
    }

    actual fun toBuilder(): TraceStateBuilderWrapper {
        TODO("Not yet implemented")
    }
}
