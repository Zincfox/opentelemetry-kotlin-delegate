@file:JsModule("@opentelemetry/api")
@file:JsNonModule
@file:Suppress("ConvertObjectToDataObject", "ProtectedInFinal")

package io.opentelemetry.kotlindelegate.js

external class ContextAPI protected constructor() {

    fun active(): Context
    fun <T> bind(context: Context, target: T): T
    fun disable()
    fun setGlobalContextManager(contextManager: ContextManager): Boolean
    fun <A, F : (args: Array<A>) -> R, R> with(
        context: Context,
        fn: F,
        thisArg: Any? = definedExternally,
        vararg args: A,
    ): R

    companion object {

        fun getInstance(): ContextAPI
    }
}

external class DiagAPI protected constructor() : DiagLogger {

    fun createComponentLogger(options: ComponentLoggerOptions): DiagLogger
    override val debug: DiagLogFunction
    fun disable()
    override val error: DiagLogFunction
    override val info: DiagLogFunction
    fun setLogger(logger: DiagLogger): Boolean
    fun setLogger(logger: DiagLogger, options: DiagLoggerOptions?): Boolean
    fun setLogger(logger: DiagLogger, logLevel: DiagLogLevel): Boolean
    override val verbose: DiagLogFunction
    override val warn: DiagLogFunction

    companion object {

        fun instance(): DiagAPI
    }
}

external class DiagConsoleLogger() : DiagLogger {

    override val debug: DiagLogFunction
    override val error: DiagLogFunction
    override val info: DiagLogFunction
    override val verbose: DiagLogFunction
    override val warn: DiagLogFunction
}

external class MetricsAPI protected constructor() {

    fun disable()
    fun getMeter(name: String, version: String? = definedExternally, options: MeterOptions? = definedExternally): Meter
    fun getMeterProvider(): MeterProvider
    fun setGlobalMeterProvider(provider: MeterProvider): Boolean

    companion object {

        fun getInstance(): MetricsAPI
    }
}

external class PropagationAPI protected constructor() {

    fun createBaggage(entries: JsRecord<String, BaggageEntry>? = definedExternally): Baggage
    fun deleteBaggage(context: Context): Context
    fun getActiveBaggage(): Baggage?
    fun getBaggage(context: Context): Baggage?
    fun setBaggage(context: Context, baggage: Baggage): Context
    fun disable()
    fun <C:Any> extract(context: Context, carrier: C, getter: TextMapGetter<C>? = definedExternally): Context
    fun fields(): Array<String>
    fun <C:Any> inject(context: Context, carrier: C, setter: TextMapSetter<C>? = definedExternally): Context
    fun setGlobalPropagator(propagator: TextMapPropagator<*>): Boolean

    companion object {

        fun getInstance(): PropagationAPI
    }
}

external class ProxyTracer(
    @Suppress("LocalVariableName")
    _provider: TracerDelegator,
    name: String,
    version: String? = definedExternally,
    options: TracerOptions? = definedExternally,
) : Tracer {

    val name: String
    val version: String?
    val options: TracerOptions?

    override fun <R> startActiveSpan(name: String, fn: (Span) -> R): R
    override fun <R> startActiveSpan(name: String, options: SpanOptions, fn: (Span) -> R): R
    override fun <R> startActiveSpan(name: String, options: SpanOptions, context: Context, fn: (Span) -> R): R

    override fun startSpan(name: String, options: SpanOptions?, context: Context?): Span
}

external class ProxyTracerProvider : TracerProvider {

    fun getDelegate(): TracerProvider
    fun getDelegateTracer(
        name: String,
        version: String? = definedExternally,
        options: TracerOptions? = definedExternally,
    ): Tracer?

    fun setDelegate(delegate: TracerProvider)

    override fun getTracer(name: String, version: String?, options: TracerOptions?): Tracer
}


external class TraceAPI protected constructor() {

    fun deleteSpan(context: Context): Context
    fun getActiveSpan(): Span?
    fun getSpan(context: Context): Span?
    fun getSpanContext(context: Context): SpanContext?
    fun isSpanContextValid(spanContext: SpanContext): Boolean
    fun setSpan(context: Context, span: Span): Context
    fun setSpanContext(context: Context, spanContext: SpanContext): Context
    fun wrapSpanContext(spanContext: SpanContext): Span
    fun disable()
    fun getTracer(name: String, version: String? = definedExternally): Tracer
    fun getTracerProvider(): TracerProvider
    fun setTracerProvider(provider: TracerProvider): Boolean

    companion object {

        fun getInstance(): TraceAPI
    }
}

external interface Attributes : JsRecord<String, AttributeValue?>
external interface Baggage {

    fun clear(): Baggage
    fun getAllEntries(): Array<JsPair<String, BaggageEntry>>
    fun getEntry(key: String): BaggageEntry?
    fun removeEntries(vararg key: String): Baggage
    fun removeEntry(key: String): Baggage
    fun setEntry(key: String, value: BaggageEntry): Baggage
}

external interface BaggageEntry {

    var metadata: BaggageEntryMetadata?
    var value: String
}

external interface BatchObservableResult<AttributeTypes : Attributes> {

    fun observe(
        `this`: BatchObservableResult<AttributeTypes>,
        metric: Observable<AttributeTypes>,
        value: Number,
        attributeTypes: AttributeTypes? = definedExternally,
    )
}

external interface ComponentLoggerOptions {

    var namespace: String
}

external interface Context {

    fun deleteValue(key: ContextKey): Context
    fun getValue(key: ContextKey): dynamic
    fun setValue(key: ContextKey, value: dynamic): Context
}

external interface ContextManager {

    fun active(): Context
    fun <T> bind(context: Context, target: T): T
    fun disable(): ContextManager
    fun enable(): ContextManager
    fun <A, F : (args: Array<A>) -> R, R> with(
        context: Context,
        fn: F,
        thisArg: Any? = definedExternally,
        vararg args: A,
    ): R
}

external interface Counter<AttributeTypes : Attributes> {

    fun add(
        value: Number,
        attributes: AttributeTypes? = definedExternally,
        context: Context? = definedExternally,
    ): Counter<AttributeTypes>
}

external interface DiagLogger {

    val debug: DiagLogFunction
    val error: DiagLogFunction
    val info: DiagLogFunction
    val verbose: DiagLogFunction
    val warn: DiagLogFunction
}

external interface DiagLoggerOptions {

    var logLevel: DiagLogLevel?
    var suppressOverrideMessage: Boolean?
}

external interface Gauge<AttributeTypes : Attributes> {

    fun record(value: Number, attributes: AttributeTypes? = definedExternally, context: Context? = definedExternally)
}

external interface Histogram<AttributeTypes : Attributes> {

    fun record(value: Number, attributes: AttributeTypes? = definedExternally, context: Context? = definedExternally)
}

external interface Link {

    var attributes: Attributes?
    var context: SpanContext
    var droppedAttributesCount: Number?
}

external interface Meter {

    fun <AttributeTypes : Attributes> addBatchObservableCallback(
        callback: BatchObservableCallback<AttributeTypes>,
        observables: Array<Observable<AttributeTypes>>,
    )

    fun <AttributeTypes : Attributes> createCounter(
        name: String,
        options: MetricOptions? = definedExternally,
    ): Counter<AttributeTypes>

    fun <AttributeTypes : Attributes> createGauge(
        name: String,
        options: MetricOptions? = definedExternally,
    ): Gauge<AttributeTypes>

    fun <AttributeTypes : Attributes> createHistogram(
        name: String,
        options: MetricOptions? = definedExternally,
    ): Histogram<AttributeTypes>

    fun <AttributeTypes : Attributes> createObservableCounter(
        name: String,
        options: MetricOptions? = definedExternally,
    ): ObservableCounter<AttributeTypes>

    fun <AttributeTypes : Attributes> createObservableGauge(
        name: String,
        options: MetricOptions? = definedExternally,
    ): ObservableGauge<AttributeTypes>

    fun <AttributeTypes : Attributes> createObservableUpDownCounter(
        name: String,
        options: MetricOptions? = definedExternally,
    ): ObservableUpDownCounter<AttributeTypes>

    fun <AttributeTypes : Attributes> createUpDownCounter(
        name: String,
        options: MetricOptions? = definedExternally,
    ): UpDownCounter<AttributeTypes>

    fun <AttributeTypes : Attributes> removeBatchObservableCallback(
        callback: BatchObservableCallback<AttributeTypes>,
        observables: Array<Observable<AttributeTypes>>,
    )
}

external interface MeterOptions {

    var schemaUrl: String?
}

external interface MeterProvider {

    fun getMeter(name: String, version: String?, options: MeterOptions?): Meter
}

external interface MetricAdvice {

    var explicitBucketBoundaries: Array<Number>?
}

external interface MetricOptions {

    var advice: MetricAdvice?
    var description: String?
    var unit: String?
    var valueType: ValueType?
}

external interface Observable<AttributesTypes : Attributes> {

    fun addCallback(callback: ObservableCallback<AttributesTypes>)
    fun removeCallback(callback: ObservableCallback<AttributesTypes>)
}

external interface ObservableResult<AttributesTypes : Attributes> {

    fun observe(
        `this`: ObservableResult<AttributesTypes>,
        value: Number,
        attributes: AttributesTypes? = definedExternally,
    )
}

@Suppress("DEPRECATION")
@Deprecated("Use the one declared in sdk-trace-base instead")
external interface Sampler {

    fun shouldSample(
        context: Context,
        traceId: String,
        spanName: String,
        spanKind: SpanKind,
        attributes: Attributes,
        links: Array<Link>,
    ): SamplingResult

    override fun toString(): String
}

@Suppress("DEPRECATION")
@Deprecated("Use the one declared in sdk-trace-base instead")
external interface SamplingResult {

    var attributes: Attributes?
    var decision: SamplingDecision
    var traceState: TraceState?
}

external interface Span {

    fun addEvent(name: String): Span
    fun addEvent(name: String, attributesOrStartTime: TimeInput): Span
    fun addEvent(name: String, attributesOrStartTime: Attributes): Span
    fun addEvent(name: String, attributesOrStartTime: Attributes, startTime: TimeInput): Span
    fun addLink(link: Link): Span
    fun addLinks(links: Array<Link>): Span
    fun end(endTime: TimeInput? = definedExternally)
    fun isRecording(): Boolean
    fun recordException(exception: OtelException, time: TimeInput? = definedExternally)
    fun setAttribute(key: String, value: AttributeValue): Span
    fun setAttributes(attributes: Attributes): Span
    fun setStatus(status: SpanStatus): Span
    fun spanContext(): SpanContext
    fun updateName(name: String): Span
}

external interface SpanContext {

    var isRemote: Boolean?
    var spanId: String
    var traceFlags: Byte
    var traceId: String
    var traceState: TraceState?
}

external interface SpanOptions {

    var attributes: Attributes?
    var kind: SpanKind?
    var links: Array<Link>?
    var root: Boolean?
    var startTime: TimeInput?
}

external interface SpanStatus {

    var code: SpanStatusCode
    var message: String?
}

external interface TextMapGetter<in Carrier : Any> {

    fun get(carrier: Carrier, key: String): Any? //undefined, string or Array<String>
    fun keys(carrier: Carrier): Array<String>
}

external interface TextMapPropagator<Carrier : Any> {

    fun extract(context: Context, carrier: Carrier, getter: TextMapGetter<Carrier>?): Context
    fun fields(): Array<String>
    fun inject(context: Context, carrier: Carrier, setter: TextMapSetter<Carrier>?)
}

external interface TextMapSetter<in Carrier : Any> {

    fun set(carrier: Carrier, key: String, value: String)
}

external interface TraceState {

    fun get(key: String): String?
    fun serialize(): String
    fun set(key: String, value: String): TraceState
    fun unset(key: String): TraceState
}

external interface Tracer {

    fun <R> startActiveSpan(name: String, fn: (Span) -> R): R
    fun <R> startActiveSpan(name: String, options: SpanOptions, fn: (Span) -> R): R
    fun <R> startActiveSpan(name: String, options: SpanOptions, context: Context, fn: (Span) -> R): R
    fun startSpan(name: String, options: SpanOptions? = definedExternally, context: Context? = definedExternally): Span
}

external interface TracerDelegator {

    fun getDelegateTracer(
        name: String,
        version: String? = definedExternally,
        options: TracerOptions? = definedExternally,
    ): Tracer?
}

external interface TracerOptions {

    var schemaUrl: String?
}

external interface TracerProvider {

    fun getTracer(
        name: String,
        version: String? = definedExternally,
        options: TracerOptions? = definedExternally,
    ): Tracer
}

external interface UpDownCounter<AttributeTypes : Attributes> {

    fun add(value: Number, attributes: AttributeTypes? = definedExternally, context: Context? = definedExternally)
}

external val INVALID_SPANID: String
external val INVALID_SPAN_CONTEXT: SpanContext
external val INVALID_TRACEID: String
external val ROOT_CONTEXT: Context
external val context: ContextAPI
external val default: DefaultValType
external val defaultTextMapGetter: TextMapGetter<Any>
external val defaultTextMapSetter: TextMapSetter<Any>
external val diag: DiagAPI
external val metrics: MetricsAPI
external val propagation: PropagationAPI
external val trace: TraceAPI

external fun baggageEntryMetadataFromString(str: String): BaggageEntryMetadata
external fun createContextKey(description: String): ContextKey
external fun createNoopMeter(): Meter
external fun createTraceState(rawTraceState: String? = definedExternally): TraceState
external fun isSpanContextValid(spanContext: SpanContext): Boolean
external fun isValidSpanId(spanId: String): Boolean
external fun isValidTraceId(traceId: String): Boolean

sealed external class DiagLogLevel {
    object ALL : DiagLogLevel
    object DEBUG : DiagLogLevel
    object ERROR : DiagLogLevel
    object INFO : DiagLogLevel
    object NONE : DiagLogLevel
    object VERBOSE : DiagLogLevel
    object WARN : DiagLogLevel
}

@Suppress("DEPRECATION", "ClassName")
@Deprecated("Use the one declared in sdk-trace-base instead")
sealed external class SamplingDecision {

    object NOT_RECORD : SamplingDecision
    object RECORD : SamplingDecision
    object RECORD_AND_SAMPLED : SamplingDecision
}

sealed external class SpanKind {
    object CLIENT : SpanKind
    object CONSUMER : SpanKind
    object INTERNAL : SpanKind
    object PRODUCER : SpanKind
    object SERVER : SpanKind
}

sealed external class SpanStatusCode {
    object ERROR : SpanStatusCode
    object OK : SpanStatusCode
    object UNSET : SpanStatusCode
}

sealed external class TraceFlags {
    object NONE : TraceFlags
    object SAMPLED : TraceFlags
}

sealed external class ValueType {
    object DOUBLE : ValueType
    object INT : ValueType
}
