package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.kotlindelegate.utils.IOpenTelemetry


expect object GlobalOpenTelemetryWrapper : IOpenTelemetry {

    fun get(): OpenTelemetryWrapper
    fun set(openTelemetry: OpenTelemetryWrapper)
}
