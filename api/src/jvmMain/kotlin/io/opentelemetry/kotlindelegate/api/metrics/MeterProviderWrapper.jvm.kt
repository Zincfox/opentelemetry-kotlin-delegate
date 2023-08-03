package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.MeterProvider
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class MeterProviderWrapper(
    override val wrappedObject: MeterProvider
) : WrapperBase<MeterProvider>(), IWrapper {

    actual fun get(instrumentationScopeName: String): MeterWrapper {
        return wrappedObject.get(instrumentationScopeName).letWrapImmutable(::MeterWrapper)
    }

    actual fun meterBuilder(instrumentationScopeName: String): MeterBuilderWrapper {
        return wrappedObject.meterBuilder(instrumentationScopeName).let(::MeterBuilderWrapper)
    }
}
