package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.api.common.AttributesStatic
import io.opentelemetry.kotlindelegate.api.trace.SpanContext
import io.opentelemetry.kotlindelegate.api.trace.SpanContextStatic
import io.opentelemetry.kotlindelegate.api.trace.SpanKind
import io.opentelemetry.kotlindelegate.api.trace.StatusCode

data class SpanData(
    val name: String,
    val kind: SpanKind = SpanKind.INTERNAL,
    val spanContext: SpanContext = SpanContextStatic.getInvalid(),
    val parentSpanId: String? = null,
    val status: StatusData = StatusData(StatusCode.UNSET),
    val startEpochNanos: Long,
    val endEpochNanos: Long,
    val hasEnded: Boolean = true,
    val attributes: Attributes = AttributesStatic.empty(),
    val events: List<EventData> = emptyList(),
    val links: List<LinkData> = emptyList(),
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
