package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class MeterProviderWrapper : IWrapper {

    fun get(instrumentationScopeName: String): MeterWrapper
    fun meterBuilder(instrumentationScopeName: String): MeterBuilderWrapper
}

fun MeterProviderWrapper.buildMeter(
    instrumentationScopeName: String,
    block: MeterBuilderWrapper.() -> Unit,
): MeterWrapper = meterBuilder(instrumentationScopeName).apply(block).build()
