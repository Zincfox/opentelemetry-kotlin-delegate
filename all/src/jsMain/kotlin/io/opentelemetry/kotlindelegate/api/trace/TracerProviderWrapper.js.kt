package io.opentelemetry.kotlindelegate.api.trace

actual class TracerProviderWrapper {
    actual companion object {

        actual val Noop: TracerProviderWrapper
            get() = TODO("Not yet implemented")
    }

    actual fun get(instrumentationScopeName: String): TracerWrapper {
        TODO("Not yet implemented")
    }

    actual fun get(
        instrumentationScopeName: String,
        instrumentationScopeVersion: String,
    ): TracerWrapper {
        TODO("Not yet implemented")
    }

    actual fun tracerBuilder(instrumentationScopeName: String): TracerBuilderWrapper {
        TODO("Not yet implemented")
    }
}
