package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.kotlindelegate.api.metrics.MeterProviderWrapper
import io.opentelemetry.kotlindelegate.api.trace.TracerProviderWrapper
import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagatorsWrapper
import io.opentelemetry.kotlindelegate.utils.IOpenTelemetry
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class OpenTelemetryWrapper(
    override val wrappedObject: OpenTelemetry
) : WrapperBase<OpenTelemetry>(), IWrapper, IOpenTelemetry {
    actual companion object {

        actual val Noop: OpenTelemetryWrapper by lazy {
            OpenTelemetry.noop().let(::OpenTelemetryWrapper)
        }

        actual fun propagating(propagators: ContextPropagatorsWrapper): OpenTelemetryWrapper {
            return OpenTelemetry.propagating(propagators.wrappedObject).let(::OpenTelemetryWrapper)
        }
    }

    override val tracerProvider: TracerProviderWrapper
        get() = wrappedObject.tracerProvider.let(::TracerProviderWrapper)
    override val meterProvider: MeterProviderWrapper
        get() = wrappedObject.meterProvider.let(::MeterProviderWrapper)
    override val propagators: ContextPropagatorsWrapper
        get() = wrappedObject.propagators.let(::ContextPropagatorsWrapper)
}
