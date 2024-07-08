package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.js.MeterProvider as JsMeterProvider

actual interface MeterProvider {

    actual fun get(instrumentationScopeName: String): Meter {
        return meterBuilder(instrumentationScopeName).build()
    }

    actual fun meterBuilder(instrumentationScopeName: String): MeterBuilder
}

internal class MeterProviderWrapper(val provider: JsMeterProvider) : MeterProvider {

    override fun meterBuilder(instrumentationScopeName: String): MeterBuilder {
        return MeterBuilderImpl(instrumentationScopeName, provider)
    }
}
