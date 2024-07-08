package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.TracerProvider as JsTracerProvider

actual interface TracerProvider {

    actual fun get(instrumentationScopeName: String): Tracer
    actual fun get(
        instrumentationScopeName: String,
        instrumentationScopeVersion: String,
    ): Tracer

    actual fun tracerBuilder(instrumentationScopeName: String): TracerBuilder {
        throw NotImplementedError()
    }
}

class TracerProviderWrapper(val provider: JsTracerProvider): TracerProvider {

    override fun get(instrumentationScopeName: String): Tracer {
        return tracerBuilder(instrumentationScopeName).build()
    }

    override fun get(instrumentationScopeName: String, instrumentationScopeVersion: String): Tracer {
        return tracerBuilder(instrumentationScopeName).setInstrumentationVersion(instrumentationScopeVersion).build()
    }

    override fun tracerBuilder(instrumentationScopeName: String): TracerBuilder {
        return TracerBuilderImpl(instrumentationScopeName, provider)
    }
}
