package io.opentelemetry.kotlindelegate.api.metrics

actual class MeterProviderWrapper : IWrapper {

    actual fun get(instrumentationScopeName: String): MeterWrapper {
        TODO("Not yet implemented")
    }

    actual fun meterBuilder(instrumentationScopeName: String): MeterBuilderWrapper {
        TODO("Not yet implemented")
    }
}
