package io.opentelemetry.kotlindelegate.api.trace

actual class TraceStateBuilderWrapper : IWrapper {

    actual fun put(
        key: String,
        value: String,
    ): TraceStateBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun remove(key: String): TraceStateBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun build(): TraceStateWrapper {
        TODO("Not yet implemented")
    }
}
