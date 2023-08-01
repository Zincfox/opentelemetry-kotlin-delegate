package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper

interface IInstrumentBuilderWrapper : IWrapper {

    var description: String
    var unit: String
}

interface IHistogramBuilderWrapper<T> : IInstrumentBuilderWrapper {

    fun build(): T
}

interface IGaugeBuilderWrapper<TObservable : Any, TObservableMeasurement : ObservableMeasurementWrapper> :
        IInstrumentBuilderWrapper {

    fun buildWithCallback(callbackWrapper: (TObservableMeasurement) -> Unit): TObservable
    fun buildObserver(): TObservableMeasurement
}

interface ICounterBuilderWrapper<T : Any, TObservable : Any, TObservableMeasurement : ObservableMeasurementWrapper> :
        IInstrumentBuilderWrapper {

    fun build(): T
    fun buildWithCallback(callback: (TObservableMeasurement) -> Unit): TObservable
    fun buildObserver(): TObservableMeasurement
}
