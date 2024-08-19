package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.trace.SpanContextStatic
import io.opentelemetry.kotlindelegate.api.trace.TraceFlagsStatic
import io.opentelemetry.kotlindelegate.api.trace.TraceStateStatic

/**
 * Traces/spans:
 *  - trace1: 00000000000000000000000000100000
 *    - spanA: 00000000001A0000
 *    - spanB: 00000000001B0000
 *      - spanBA: 00000000001BA000
 *      - spanBB: 00000000001BB000
 *        - spanBBA: 00000000001BBA00
 *          - spanBBAA: 00000000001BBAA0
 *  - trace2: 00000000000000000000000000200000
 *    - spanA: 00000000002A0000
 *  - trace3: 00000000000000000000000000300000
 *    - spanA: 00000000003A0000
 *  - trace4: 00000000000000000000000000400000
 *    - spanA: 00000000004A0000
 */

@Suppress("unused")
object TraceTestData {

    val trace1SpanA: SpanData = SpanData(
        name = "spanA",
        parentSpanId = null,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000100000",
            "00000000001a0000",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 100L,
        endEpochNanos = 500L,
    )
    val trace1SpanB: SpanData = SpanData(
        name = "spanB",
        parentSpanId = null,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000100000",
            "00000000001b0000",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 200L,
        endEpochNanos = 800L,
    )
    val trace1SpanBA: SpanData = SpanData(
        name = "spanBA",
        parentSpanId = trace1SpanB.spanId,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000100000",
            "00000000001ba000",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 210L,
        endEpochNanos = 300L,
    )
    val trace1SpanBB: SpanData = SpanData(
        name = "spanBB",
        parentSpanId = trace1SpanB.spanId,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000100000",
            "00000000001bb000",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 400L,
        endEpochNanos = 790L,
    )
    val trace1SpanBBA: SpanData = SpanData(
        name = "spanBBA",
        parentSpanId = trace1SpanBB.spanId,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000100000",
            "00000000001bba00",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 500L,
        endEpochNanos = 780L,
    )
    val trace1SpanBBAA: SpanData = SpanData(
        name = "spanBBAA",
        parentSpanId = trace1SpanBBA.spanId,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000100000",
            "00000000001bbaa0",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 600L,
        endEpochNanos = 770L,
    )
    val trace2SpanA: SpanData = SpanData(
        name = "spanA",
        parentSpanId = null,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000200000",
            "00000000002a0000",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 1_000_000_000L,
        endEpochNanos = 2_000_000_000L,
    )
    val trace3SpanA: SpanData = SpanData(
        name = "spanA",
        parentSpanId = null,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000300000",
            "00000000003a0000",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 3_000_000_000L,
        endEpochNanos = 4_000_000_000L,
    )
    val trace4SpanA: SpanData = SpanData(
        name = "spanA",
        parentSpanId = null,
        spanContext = SpanContextStatic.create(
            "00000000000000000000000000400000",
            "00000000004a0000",
            TraceFlagsStatic.fromByte(0x01), //sampled
            TraceStateStatic.getDefault()
        ),
        startEpochNanos = 3_000_000_000L,
        endEpochNanos = 4_000_000_000L,
    )

    val allSpans: Map<String, List<SpanData>> = listOf(
        trace1SpanA,
        trace1SpanB,
        trace1SpanBA,
        trace1SpanBB,
        trace1SpanBBA,
        trace1SpanBBAA,
        trace2SpanA,
        trace3SpanA,
        trace4SpanA,
    ).groupBy { it.traceId }
}
