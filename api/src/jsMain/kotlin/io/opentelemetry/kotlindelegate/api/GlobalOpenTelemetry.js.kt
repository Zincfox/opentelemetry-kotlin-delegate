package io.opentelemetry.kotlindelegate.api

actual val GlobalOpenTelemetry: OpenTelemetry
    get() = OpenTelemetryImpl
