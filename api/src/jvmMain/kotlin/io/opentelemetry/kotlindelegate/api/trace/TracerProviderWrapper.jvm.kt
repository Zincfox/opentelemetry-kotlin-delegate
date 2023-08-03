package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.TracerProvider
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class TracerProviderWrapper(
    override val wrappedObject: TracerProvider
) : WrapperBase<TracerProvider>(), IWrapper {
    actual companion object {

        actual val Noop: TracerProviderWrapper by lazy {
            TracerProvider.noop().let(::TracerProviderWrapper)
        }
    }

    actual fun get(instrumentationScopeName: String): TracerWrapper {
        return wrappedObject.get(instrumentationScopeName).let(::TracerWrapper)
    }

    actual fun get(
        instrumentationScopeName: String,
        instrumentationScopeVersion: String,
    ): TracerWrapper {
        return wrappedObject.get(instrumentationScopeName, instrumentationScopeVersion).let(::TracerWrapper)
    }

    actual fun tracerBuilder(instrumentationScopeName: String): TracerBuilderWrapper {
        return wrappedObject.tracerBuilder(instrumentationScopeName).let(::TracerBuilderWrapper)
    }
}
