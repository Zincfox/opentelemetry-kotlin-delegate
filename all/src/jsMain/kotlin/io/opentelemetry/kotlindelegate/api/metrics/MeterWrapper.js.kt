package io.opentelemetry.kotlindelegate.api.metrics

actual class MeterWrapper : IWrapper {

    actual fun longCounterBuilder(name: String): LongCounterBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun longUpDownCounterBuilder(name: String): LongUpDownCounterBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun doubleHistogramBuilder(name: String): DoubleHistogramBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun doubleGaugeBuilder(name: String): DoubleGaugeBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun doubleCounterBuilder(name: String): DoubleCounterBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun doubleUpDownCounterBuilder(name: String): DoubleUpDownCounterBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun longHistogramBuilder(name: String): LongHistogramBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun longGaugeBuilder(name: String): LongGaugeBuilderWrapper {
        TODO("Not yet implemented")
    }

    actual fun batchCallback(
        observableMeasurement: ObservableMeasurementWrapper,
        vararg additionalMeasurements: ObservableMeasurementWrapper,
        callback: () -> Unit,
    ): BatchCallbackWrapper {
        TODO("Not yet implemented")
    }
}
