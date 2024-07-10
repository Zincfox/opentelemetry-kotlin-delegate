package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.api.trace.SpanContext
import io.opentelemetry.kotlindelegate.api.trace.SpanKind
import io.opentelemetry.kotlindelegate.api.trace.StatusCode

data class SpanData(
    val name: String,
    val kind: SpanKind,
    val spanContext: SpanContext,
    val parentSpanId: String?,
    val status: StatusData,
    val startEpochNanos: Long,
    val endEpochNanos: Long,
    val hasEnded: Boolean,
    val attributes: Attributes,
    val events: List<EventData>,
    val links: List<LinkData>,
) : Comparable<SpanData> {
    val traceId: String
        get() = spanContext.getTraceId()
    val spanId: String
        get() = spanContext.getSpanId()
    val statusCode: StatusCode
        get() = status.statusCode
    override fun compareTo(other: SpanData): Int {
        return startEpochNanos.compareTo(other.startEpochNanos)
    }

    data class LinkData(
        val spanContext: SpanContext,
        val attributes: Attributes,
    )

    data class EventData(
        val name: String,
        val attributes: Attributes,
        val epochNanos: Long,
    )

    data class StatusData(
        val statusCode: StatusCode,
        val description: String="",
    )
}
