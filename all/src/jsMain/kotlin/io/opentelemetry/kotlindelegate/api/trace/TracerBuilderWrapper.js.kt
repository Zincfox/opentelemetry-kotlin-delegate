package io.opentelemetry.kotlindelegate.api.trace

actual class TracerBuilderWrapper : IWrapper {

    actual var schemaUrl: String
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var instrumentationVersion: String
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun build(): TracerWrapper {
        TODO("Not yet implemented")
    }
}
