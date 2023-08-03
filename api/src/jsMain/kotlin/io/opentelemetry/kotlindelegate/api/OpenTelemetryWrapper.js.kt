package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.kotlindelegate.api.metrics.MeterProviderWrapper
import io.opentelemetry.kotlindelegate.api.trace.TracerProviderWrapper
import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagatorsWrapper
import io.opentelemetry.kotlindelegate.utils.IOpenTelemetry
import io.opentelemetry.kotlindelegate.utils.IWrapper

actual class OpenTelemetryWrapper : IWrapper, IOpenTelemetry {

    //val logsBridge: LoggerProviderWrapper
    actual companion object {

        actual val Noop: OpenTelemetryWrapper
            get() = TODO("Not yet implemented")

        actual fun propagating(propagators: ContextPropagatorsWrapper): OpenTelemetryWrapper {
            TODO("Not yet implemented")
        }
    }

    actual val tracerProvider: TracerProviderWrapper
        get() = TODO("Not yet implemented")
    actual val meterProvider: MeterProviderWrapper
        get() = TODO("Not yet implemented")
    actual val propagators: ContextPropagatorsWrapper
        get() = TODO("Not yet implemented")
}
