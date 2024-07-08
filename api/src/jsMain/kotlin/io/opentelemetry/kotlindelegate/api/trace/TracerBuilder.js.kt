package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.TracerOptions
import io.opentelemetry.kotlindelegate.js.TracerProvider as JsTracerProvider

actual interface TracerBuilder {

    actual fun setSchemaUrl(schemaUrl: String): TracerBuilder
    actual fun setInstrumentationVersion(instrumentationScopeVersion: String): TracerBuilder
    actual fun build(): Tracer
}

internal class TracerBuilderImpl(val name: String, val provider: JsTracerProvider) : TracerBuilder {

    var schemaUrl: String? = null
    var instrumentationScopeVersion: String? = null
    override fun setSchemaUrl(schemaUrl: String): TracerBuilder {
        this.schemaUrl = schemaUrl
        return this
    }

    override fun setInstrumentationVersion(instrumentationScopeVersion: String): TracerBuilder {
        this.instrumentationScopeVersion = instrumentationScopeVersion
        return this
    }

    override fun build(): Tracer {
        val options: TracerOptions = js("{}").unsafeCast<TracerOptions>()
        if (schemaUrl != null) {
            options.schemaUrl = schemaUrl
        }
        return if (instrumentationScopeVersion != null) {
            TracerWrapper(provider.getTracer(name, instrumentationScopeVersion, options))
        } else {
            TracerWrapper(provider.getTracer(name, options = options))
        }
    }
}
