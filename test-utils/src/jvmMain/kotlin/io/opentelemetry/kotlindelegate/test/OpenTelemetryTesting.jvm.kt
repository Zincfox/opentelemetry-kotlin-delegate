package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.sdk.trace.export.SpanExporter


internal actual fun registerWithNewTraceForestRecorder(): TraceForestRecorder {

    val recorder = TraceForestRecorderImpl(false)
    OpenTelemetrySdk.builder().apply {
        setTracerProvider(SdkTracerProvider.builder().apply {
            addSpanProcessor(
                SimpleSpanProcessor.create(
                SpanExporter.composite(LoggingSpanExporter.create(), recorder)
            ))
        }.build())
    }.buildAndRegisterGlobal()
    return recorder
}
