package io.opentelemetry.kotlindelegate.js

import kotlin.js.Promise

typealias AttributeValue = Any
typealias BaggageEntryMetadata = Any
typealias BatchObservableCallback<AttributeTypes> = (BatchObservableResult<AttributeTypes>) -> Promise<Unit>?
typealias DiagLogFunction = (message: String, args: Array<dynamic>) -> Promise<Unit>
typealias OtelException = Any
typealias HrTime = JsPair<Number, Number>
@Deprecated("use AttributeValue")
typealias MetricAttributeValue = AttributeValue
@Deprecated("use Attributes")
typealias MetricAttributes = Any
typealias ObservableCallback<AttributesTypes> = (observableResult: ObservableResult<AttributesTypes>) -> Promise<Unit>?
typealias ObservableCounter<AttributesTypes> = Observable<AttributesTypes>
typealias ObservableGauge<AttributesTypes> = Observable<AttributesTypes>
typealias ObservableUpDownCounter<AttributesTypes> = Observable<AttributesTypes>
@Deprecated("use AttributeValue")
typealias SpanAttributeValue = AttributeValue
@Deprecated("use Attributes")
typealias SpanAttributes = Attributes
typealias TimeInput = Any //hrtime, epoch milliseconds, performance.now() or Date

external interface DefaultValType {
    val context: ContextAPI
    val diag: DiagAPI
    val metrics: MetricsAPI
    val propagation: PropagationAPI
    val trace: TraceAPI
}

external interface ContextKey
