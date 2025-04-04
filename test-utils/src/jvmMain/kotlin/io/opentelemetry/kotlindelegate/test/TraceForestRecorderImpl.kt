package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.trace.export.SpanExporter
import io.opentelemetry.sdk.trace.data.SpanData as JvmSpanData

class TraceForestRecorderImpl(startActive: Boolean) :
        AbstractTraceForestRecorder(startActive), SpanExporter {

    constructor() : this(false)

    override fun export(spans: MutableCollection<JvmSpanData>): CompletableResultCode {
        spans.forEach { span ->
            store(SpanData(
                name = span.name,
                kind = span.kind,
                spanContext = span.spanContext,
                parentSpanId = span.parentSpanContext.let { if(it.isValid) it.spanId else null },
                status = SpanData.StatusData(span.status.statusCode, span.status.description),
                startEpochNanos = span.startEpochNanos,
                endEpochNanos = span.endEpochNanos,
                hasEnded = span.hasEnded(),
                attributes = span.attributes,
                events = span.events.map { event ->
                    SpanData.EventData(
                        name = event.name,
                        attributes = event.attributes,
                        epochNanos = event.epochNanos,
                    )
                },
                links = span.links.map { link ->
                    SpanData.LinkData(
                        spanContext = link.spanContext,
                        attributes = link.attributes,
                    )
                }
            ))
        }
        return CompletableResultCode.ofSuccess()
    }

    override fun flush(): CompletableResultCode {
        return CompletableResultCode.ofSuccess()
    }

    override fun shutdown(): CompletableResultCode {
        return CompletableResultCode.ofSuccess()
    }
}
