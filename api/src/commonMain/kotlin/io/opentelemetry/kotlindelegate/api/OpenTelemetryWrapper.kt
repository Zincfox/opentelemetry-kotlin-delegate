package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.kotlindelegate.context.propagation.ContextPropagatorsWrapper
import io.opentelemetry.kotlindelegate.utils.IOpenTelemetry
import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class OpenTelemetryWrapper : IOpenTelemetry, IWrapper {
    companion object {

        val Noop: OpenTelemetryWrapper
        fun propagating(propagators: ContextPropagatorsWrapper): OpenTelemetryWrapper
    }
}
