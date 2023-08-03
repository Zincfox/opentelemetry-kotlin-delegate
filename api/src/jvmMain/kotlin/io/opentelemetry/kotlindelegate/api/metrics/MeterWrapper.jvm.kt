package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.Meter
import io.opentelemetry.api.metrics.ObservableMeasurement
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class MeterWrapper(
    override val wrappedObject: Meter
) : WrapperBase<Meter>(), IWrapper {

    actual fun longCounterBuilder(name: String): LongCounterBuilderWrapper {
        return wrappedObject.counterBuilder(name).let(::LongCounterBuilderWrapper)
    }

    actual fun longUpDownCounterBuilder(name: String): LongUpDownCounterBuilderWrapper {
        return wrappedObject.upDownCounterBuilder(name).let(::LongUpDownCounterBuilderWrapper)
    }

    actual fun doubleHistogramBuilder(name: String): DoubleHistogramBuilderWrapper {
        return wrappedObject.histogramBuilder(name).let(::DoubleHistogramBuilderWrapper)
    }

    actual fun doubleGaugeBuilder(name: String): DoubleGaugeBuilderWrapper {
        return wrappedObject.gaugeBuilder(name).let(::DoubleGaugeBuilderWrapper)
    }

    actual fun doubleCounterBuilder(name: String): DoubleCounterBuilderWrapper {
        return wrappedObject.counterBuilder(name).ofDoubles().let(::DoubleCounterBuilderWrapper)
    }

    actual fun doubleUpDownCounterBuilder(name: String): DoubleUpDownCounterBuilderWrapper {
        return wrappedObject.upDownCounterBuilder(name).ofDoubles().let(::DoubleUpDownCounterBuilderWrapper)
    }

    actual fun longHistogramBuilder(name: String): LongHistogramBuilderWrapper {
        return wrappedObject.histogramBuilder(name).ofLongs().let(::LongHistogramBuilderWrapper)
    }

    actual fun longGaugeBuilder(name: String): LongGaugeBuilderWrapper {
        return wrappedObject.gaugeBuilder(name).ofLongs().let(::LongGaugeBuilderWrapper)
    }

    actual fun batchCallback(
        observableMeasurement: ObservableMeasurementWrapper,
        vararg additionalMeasurements: ObservableMeasurementWrapper,
        callback: () -> Unit,
    ): BatchCallbackWrapper {
        return wrappedObject.batchCallback(
            callback,
            observableMeasurement.wrappedObject,
            *(additionalMeasurements.map { it.wrappedObject }.toTypedArray())
        ).letWrapImmutable(::BatchCallbackWrapper)
    }
}
