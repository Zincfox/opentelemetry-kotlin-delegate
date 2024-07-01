package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.MeterOptions as JsMeterOptions
import io.opentelemetry.kotlindelegate.js.MeterProvider as JsMeterProvider
import io.opentelemetry.kotlindelegate.js.Meter as JsMeter

actual interface MeterBuilder {

    actual fun setSchemaUrl(schemaUrl: String): MeterBuilder
    actual fun setInstrumentationVersion(instrumentationVersion: String): MeterBuilder
    actual fun build(): Meter
}

internal class MeterBuilderImpl(val name: String, val provider: JsMeterProvider) : MeterBuilder {

    var schemaUrl: String? = null
    var instrumentationVersion: String? = null
    override fun setSchemaUrl(schemaUrl: String): MeterBuilder {
        this.schemaUrl = schemaUrl
        return this
    }

    override fun setInstrumentationVersion(instrumentationVersion: String): MeterBuilder {
        this.instrumentationVersion = instrumentationVersion
        return this
    }

    override fun build(): Meter {
        val options: JsMeterOptions = js("{}").unsafeCast<JsMeterOptions>()
        if (schemaUrl != null) {
            options.schemaUrl = schemaUrl
        }
        val meter: JsMeter = if (instrumentationVersion != null)
            provider.getMeter(name, instrumentationVersion, options)
        else
            provider.getMeter(name, options = options)
        return MeterWrapper(meter)
    }
}
