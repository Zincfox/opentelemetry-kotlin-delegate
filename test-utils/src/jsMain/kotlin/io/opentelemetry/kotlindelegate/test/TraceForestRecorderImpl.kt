package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.common.AttributesStatic
import io.opentelemetry.kotlindelegate.api.common.asCommonAttributes
import io.opentelemetry.kotlindelegate.api.trace.asCommonSpanContext
import io.opentelemetry.kotlindelegate.api.trace.asCommonSpanKind
import io.opentelemetry.kotlindelegate.api.trace.asCommonStatusCode
import io.opentelemetry.kotlindelegate.js.toNanos
import kotlin.js.Promise

class TraceForestRecorderImpl(startActive: Boolean) :
        AbstractTraceForestRecorder(startActive), SpanExporter {

    constructor() : this(false)

    override fun export(spans: Array<ReadableSpan>, resultCallback: (result: ExportResult) -> Unit) {
        if(!isActive) {
            resultCallback(object: ExportResult {
                override val error: Throwable? = null
                override val code: ExportResultCode
                    get() = ExportResultCode.SUCCESS
            })
            return
        }
        try {
            spans.forEach { span ->
                val startTime: Long = span.startTime.toNanos()
                val duration: Long = span.duration.toNanos()
                val endTime = startTime + duration
                storeIfActive(
                    SpanData(
                        name = span.name,
                        kind = span.kind.asCommonSpanKind(),
                        spanContext = span.spanContext().asCommonSpanContext(),
                        parentSpanId = span.parentSpanId.takeUnless { it.isNullOrEmpty() },
                        status = SpanData.StatusData(
                            statusCode = span.status.code.asCommonStatusCode(),
                            description = span.status.message.orEmpty()
                        ),
                        startEpochNanos = startTime,
                        endEpochNanos = endTime,
                        hasEnded = span.ended,
                        attributes = span.attributes.asCommonAttributes(),
                        events = span.events.map {
                            SpanData.EventData(
                                name = it.name,
                                attributes = it.attributes?.asCommonAttributes() ?: AttributesStatic.empty(),
                                epochNanos = it.time.toNanos()
                            )
                        },
                        links = span.links.map {
                            SpanData.LinkData(
                                spanContext = it.context.asCommonSpanContext(),
                                attributes = it.attributes?.asCommonAttributes() ?: AttributesStatic.empty()
                            )
                        },
                    )
                )
            }
        } catch (ex: Exception) {
            resultCallback(object : ExportResult {
                override val code: ExportResultCode = ExportResultCode.FAILED
                override val error: Throwable = ex
            })
            throw ex
        }
        resultCallback(object : ExportResult {
            override val code: ExportResultCode = ExportResultCode.SUCCESS
            override val error: Throwable? = null
        })
    }

    override val forceFlush: (() -> Promise<Unit>) = { Promise.resolve(Unit) }

    override fun shutdown(): Promise<Unit> {
        return Promise.resolve(Unit)
    }
}
