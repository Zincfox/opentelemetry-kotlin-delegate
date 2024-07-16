package io.opentelemetry.kotlindelegate.test

object OpenTelemetryTesting {

    var recorder: TraceForestRecorder? = null
    val isConfigured: Boolean
        get() = recorder != null
    fun defaultSetup(): TraceForestRecorder {
        recorder?.let { return it }
        return registerWithNewTraceForestRecorder().also { recorder = it }
    }

}

internal expect fun registerWithNewTraceForestRecorder(): TraceForestRecorder
