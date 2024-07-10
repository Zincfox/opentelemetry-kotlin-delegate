@file:JsModule("@opentelemetry/sdk-trace-base")
@file:JsNonModule
@file:Suppress("ConvertObjectToDataObject", "ClassName")

package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.js.*
import io.opentelemetry.kotlindelegate.js.Span as ISpan
import io.opentelemetry.kotlindelegate.js.Tracer as JsTracer
import kotlin.js.Promise

external class AlwaysOffSampler : Sampler {

    override fun toString(): String
    override fun shouldSample(
        context: Context,
        traceId: String,
        spanName: String,
        spanKind: SpanKind,
        attributes: Attributes,
        links: Array<Link>,
    ): SamplingResult
}

external class AlwaysOnSampler : Sampler {

    override fun toString(): String
    override fun shouldSample(
        context: Context,
        traceId: String,
        spanName: String,
        spanKind: SpanKind,
        attributes: Attributes,
        links: Array<Link>,
    ): SamplingResult
}

external class BasicTracerProvider(config: TracerConfig = definedExternally) : TracerProvider {

    var activeSpanProcessor: SpanProcessor

    //val resource: IResource
    fun addSpanProcessor(processor: SpanProcessor)
    fun forceFlush(): Promise<Unit>
    fun getActiveSpanProcessor(): SpanProcessor
    override fun getTracer(name: String, version: String?, options: TracerOptions?): Tracer
    fun register(config: SDKRegistrationConfig = definedExternally)
    fun shutdown(): Promise<Unit>
}

external class BatchSpanProcessor(_exporter: SpanExporter, config: BufferConfig? = definedExternally) : SpanProcessor {

    override fun forceFlush(): Promise<Unit>
    override fun onEnd(span: ReadableSpan)
    override fun onStart(span: Span, parentContext: Context)
    override fun shutdown(): Promise<Unit>
}

external class ConsoleSpanExporter : SpanExporter {

    override fun export(spans: Array<ReadableSpan>, resultCallback: (result: ExportResult) -> Unit)
    override val forceFlush: (() -> Promise<Unit>)
    override fun shutdown(): Promise<Unit>
}

external class InMemorySpanExporter : SpanExporter {

    override fun export(spans: Array<ReadableSpan>, resultCallback: (result: ExportResult) -> Unit)
    override val forceFlush: (() -> Promise<Unit>)
    fun getFinishedSpans(): Array<ReadableSpan>
    fun reset()
    override fun shutdown(): Promise<Unit>
}

external class NoopSpanProcessor : SpanProcessor {

    override fun forceFlush(): Promise<Unit>
    override fun onEnd(span: ReadableSpan)
    override fun onStart(span: Span, parentContext: Context)
    override fun shutdown(): Promise<Unit>
}

external class ParentBasedSampler(config: ParentBasedSamplerConfig) : Sampler {

    override fun shouldSample(
        context: Context,
        traceId: String,
        spanName: String,
        spanKind: SpanKind,
        attributes: Attributes,
        links: Array<Link>,
    ): SamplingResult

    override fun toString(): String
}

external interface ParentBasedSamplerConfig {

    var localParentNotSampled: Sampler?
    var localParentSampled: Sampler?
    var remoteParentNotSampled: Sampler?
    var remoteParentSampled: Sampler?
    var root: Sampler
}

external class RandomIdGenerator : IdGenerator {

    override fun generateSpanId(): String
    override fun generateTraceId(): String
}

external class SimpleSpanProcessor(exporter: SpanExporter) : SpanProcessor {

    override fun forceFlush(): Promise<Unit>
    override fun onEnd(span: ReadableSpan)
    override fun onStart(span: Span, parentContext: Context)
    override fun shutdown(): Promise<Unit>
}

external class Span protected constructor() : ReadableSpan, ISpan {

    override val attributes: Attributes
    override val droppedAttributeCount: Number
    override val droppedEventsCount: Number
    override val duration: HrTime
    override val ended: Boolean
    override val events: Array<TimedEvent>
    override val instrumentationLibrary: InstrumentationScope
    override val kind: SpanKind
    override val links: Array<Link>
    override val name: String
    override val parentSpanId: String?
    override val resource: IResource
    override val startTime: HrTime
    override val status: SpanStatus

    override fun addEvent(name: String): ISpan

    override fun addEvent(name: String, attributesOrStartTime: Attributes): ISpan

    override fun addEvent(
        name: String,
        attributesOrStartTime: Attributes,
        startTime: TimeInput,
    ): ISpan

    override fun addEvent(name: String, attributesOrStartTime: TimeInput): ISpan
    override fun addLink(link: Link): ISpan
    override fun addLinks(links: Array<Link>): ISpan
    override fun end(endTime: TimeInput?)
    override fun isRecording(): Boolean
    override fun recordException(exception: OtelException, time: TimeInput?)
    override fun setAttribute(key: String, value: AttributeValue): ISpan
    override fun setAttributes(attributes: Attributes): ISpan
    override fun setStatus(status: SpanStatus): ISpan
    override fun updateName(name: String): ISpan
    override fun spanContext(): SpanContext
}

external class TraceIdRatioBasedSampler(ratio: Number = definedExternally) : Sampler {

    override fun toString(): String
    override fun shouldSample(
        context: Context,
        traceId: String,
        spanName: String,
        spanKind: SpanKind,
        attributes: Attributes,
        links: Array<Link>,
    ): SamplingResult
}

external class Tracer(
    instrumentationLibrary: InstrumentationScope,
    config: TracerConfig,
    tracerProvider: BasicTracerProvider,
) : JsTracer {

    override fun <R> startActiveSpan(name: String, fn: (ISpan) -> R): R

    override fun <R> startActiveSpan(
        name: String,
        options: SpanOptions,
        fn: (ISpan) -> R,
    ): R

    override fun <R> startActiveSpan(
        name: String,
        options: SpanOptions,
        context: Context,
        fn: (ISpan) -> R,
    ): R

    override fun startSpan(
        name: String,
        options: SpanOptions?,
        context: Context?,
    ): ISpan

    fun getActiveSpanProcessor(): SpanProcessor
    fun getGeneralLimits(): GeneralLimits
    fun getSpanLimits(): SpanLimits
}

sealed external class ForceFlushState {
    object error : ForceFlushState
    object resolved : ForceFlushState
    object timeout : ForceFlushState
    object unresolved : ForceFlushState
}

sealed external class SamplingDecision {
    object NOT_RECORD : SamplingDecision
    object RECORD : SamplingDecision
    object RECORD_AND_SAMPLED : SamplingDecision
}

external interface BatchSpanProcessorBrowserConfig : BufferConfig {
    var disableAutoFlushOnDocumentHide: Boolean?
}

external interface BufferConfig {
    var exportTimeoutMillis: Number?
    var maxExportBatchSize: Number?
    var maxQueueSize: Number?
    var scheduledDelayMillis: Number?
}

external interface GeneralLimits {
    var attributeCountLimit: Number?
    var attributeValueLengthLimit: Number?
}

external interface IdGenerator {

    fun generateSpanId(): String
    fun generateTraceId(): String
}

external interface ReadableSpan {

    val attributes: Attributes
    val droppedAttributeCount: Number
    val droppedEventsCount: Number
    val duration: HrTime
    val ended: Boolean
    val events: Array<TimedEvent>
    val instrumentationLibrary: InstrumentationScope
    val kind: SpanKind
    val links: Array<Link>
    val name: String
    val parentSpanId: String?
    val resource: IResource
    val startTime: HrTime
    val status: SpanStatus
    fun spanContext(): SpanContext
}

external interface InstrumentationScope {

    val name: String
    val schemaUrl: String?
    val version: String?
}

external interface IResource {

    val attributes: Array<IKeyValue>
    val droppedAttributesCount: Number
}

external interface IKeyValue {

    val key: String
    val value: Any
}

external interface SDKRegistrationConfig {
    var contextManager: ContextManager?
    var propagator: TextMapPropagator<*>?
}

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

external interface SamplingResult {
    var attributes: Attributes?
    var decision: SamplingDecision
    var traceState: TraceState?
}

//actually in @opentelemetry/core
external interface ExportResult {

    val code: ExportResultCode
    val error: Throwable?
}

sealed external class ExportResultCode {
    object FAILED : ExportResultCode
    object SUCCESS : ExportResultCode
}

external interface SpanExporter {

    fun export(spans: Array<ReadableSpan>, resultCallback: ((result: ExportResult) -> Unit))
    val forceFlush: (() -> Promise<Unit>)?
    fun shutdown(): Promise<Unit>
}

external interface SpanLimits {
    var attributeCountLimit: Number?
    var attributePerEventCountLimit: Number?
    var attributePerLinkCountLimit: Number?
    var attributeValueLengthLimit: Number?
    var eventCountLimit: Number?
    var linkCountLimit: Number?
}

external interface SpanProcessor {

    fun forceFlush(): Promise<Unit>
    fun onEnd(span: ReadableSpan)
    fun onStart(span: Span, parentContext: Context)
    fun shutdown(): Promise<Unit>
}

external interface TimedEvent {
    var attributes: Attributes?
    var droppedAttributesCount: Number?
    var name: String
    var time: HrTime
}

external interface TracerConfig {
    var forceFlushTimeoutMillis: Number?
    var generalLimits: GeneralLimits?
    var idGenerator: IdGenerator?
    var resource: IResource?
    var sampler: Sampler?
    var spanLimits: SpanLimits?
}
