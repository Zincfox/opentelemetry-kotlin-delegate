package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.js.ContextManager

@Suppress("UNUSED_VARIABLE")
internal actual fun registerWithNewTraceForestRecorder(): TraceForestRecorder {
    val contextZoneModule = js("""require("@opentelemetry/context-zone")""")
    val contextManager = js("""new contextZoneModule.ZoneContextManager()""").unsafeCast<ContextManager>()
    val recorder = TraceForestRecorderImpl()
    val provider = BasicTracerProvider()
    provider.addSpanProcessor(SimpleSpanProcessor(ConsoleSpanExporter()))
    provider.addSpanProcessor(SimpleSpanProcessor(recorder))
    provider.register(js("{}").unsafeCast<SDKRegistrationConfig>().also { config ->
        config.contextManager = contextManager.enable()
    })
    return recorder
}
