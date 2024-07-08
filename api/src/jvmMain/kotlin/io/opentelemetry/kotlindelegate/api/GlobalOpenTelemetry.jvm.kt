package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.api.OpenTelemetry

actual val GlobalOpenTelemetry: OpenTelemetry
    get() = io.opentelemetry.api.GlobalOpenTelemetry.get()
