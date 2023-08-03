package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class TracerProviderWrapper : IWrapper {
    companion object {

        val Noop: TracerProviderWrapper
    }

    fun get(instrumentationScopeName: String): TracerWrapper
    fun get(instrumentationScopeName: String, instrumentationScopeVersion: String): TracerWrapper
    fun tracerBuilder(instrumentationScopeName: String): TracerBuilderWrapper
}

fun TracerProviderWrapper.buildTracer(
    instrumentationScopeName: String,
    configuration: TracerBuilderWrapper.() -> Unit,
): TracerWrapper = tracerBuilder(instrumentationScopeName).apply(configuration).build()
