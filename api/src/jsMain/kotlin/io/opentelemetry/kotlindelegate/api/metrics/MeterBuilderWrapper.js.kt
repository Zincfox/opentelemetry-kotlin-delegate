package io.opentelemetry.kotlindelegate.api.metrics

actual class MeterBuilderWrapper : IWrapper {

    actual var schemaUrl: String
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var instrumentationVersion: String
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun build(): MeterWrapper {
        TODO("Not yet implemented")
    }
}
