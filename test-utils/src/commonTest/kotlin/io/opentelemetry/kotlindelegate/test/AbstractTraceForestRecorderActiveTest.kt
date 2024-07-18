@file:Suppress("MemberVisibilityCanBePrivate")

package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.trace.SpanContextStatic
import io.opentelemetry.kotlindelegate.api.trace.TraceFlagsStatic
import io.opentelemetry.kotlindelegate.api.trace.TraceStateStatic
import kotlin.test.*

class AbstractTraceForestRecorderActiveTest {

    lateinit var recorder: AbstractTraceForestRecorder

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
    }

    @BeforeTest
    fun setup() {
        recorder = TraceForestRecorderTestImpl(startActive = false)
        assertEquals(emptyList(), recorder.traces, "Expected no initial traces after setup")
    }

    @Test
    fun testInitialActiveRecording() {
        val rec = TraceForestRecorderTestImpl(startActive = true)
        assertTrue(rec.isActive, "rec.isActive is false for expected active recorder")
        rec.storeIfActive(TraceTestData.trace1SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId),
            rec.traces,
            "Expected traces when recording with active recorder"
        )
    }

    @Test
    fun testInitialInactiveNotRecording() {
        val rec = TraceForestRecorderTestImpl(startActive = false)
        assertFalse(rec.isActive, "rec.isActive is true for expected inactive recorder")
        rec.storeIfActive(TraceTestData.trace1SpanA)
        assertEquals(emptyList(), rec.traces, "Expected no traces when recording with inactive recorder")
    }

    @Test
    fun testClear() {
        assertEquals(emptyList(), recorder.traces, "Expected no initial traces")
        assertFalse(recorder.isActive, "Recorder started active")
        assertTrue(recorder.activate(), "Activate returned false")
        assertTrue(recorder.isActive, "rec.isActive is false for expected active recorder")
        recorder.storeIfActive(TraceTestData.trace1SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId),
            recorder.traces,
            "Expected traces when recording with active recorder"
        )
        assertTrue(recorder.deactivate(), "Deactivate returned false")
        assertEquals(listOf(TraceTestData.trace1SpanA.traceId), recorder.traces, "Traces changed by deactivate")
        recorder.clear()
        assertEquals(emptyList(), recorder.traces, "Expected no traces after clear")
        assertFalse(recorder.isActive, "IsActive true after clear")
    }

    @Test
    fun testActivateDeactivate() {
        assertEquals(emptyList(), recorder.traces, "Expected no initial traces")
        assertFalse(recorder.isActive, "Recorder started active")
        assertTrue(recorder.activate(), "Activate returned false")
        assertTrue(recorder.isActive, "rec.isActive is false for expected active recorder")
        recorder.storeIfActive(TraceTestData.trace1SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId),
            recorder.traces,
            "Expected traces when recording with active recorder"
        )
        assertTrue(recorder.deactivate(), "Deactivate returned false")
        assertEquals(listOf(TraceTestData.trace1SpanA.traceId), recorder.traces, "Traces changed by deactivate")
        assertFalse(recorder.isActive, "IsActive true after deactivate")
    }

    @Test
    fun testDoubleActivateActivateDoubleDeactivate() {
        assertEquals(emptyList(), recorder.traces, "Expected no initial traces")
        assertFalse(recorder.isActive, "Recorder started active")
        assertTrue(recorder.activate(), "First activate returned false")
        assertTrue(recorder.isActive, "rec.isActive is false after first activate")
        recorder.storeIfActive(TraceTestData.trace1SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId),
            recorder.traces,
            "Expected recording of one trace after first activate"
        )
        assertFalse(recorder.activate(), "Second activate returned true")
        assertTrue(recorder.isActive, "rec.isActive is false after second activate")
        recorder.storeIfActive(TraceTestData.trace2SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId, TraceTestData.trace2SpanA.traceId),
            recorder.traces,
            "Expected recording of two traces after second activate"
        )
        assertTrue(recorder.deactivate(), "First deactivate returned false")
        assertFalse(recorder.isActive, "IsActive true after first deactivate")
        recorder.storeIfActive(TraceTestData.trace3SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId, TraceTestData.trace2SpanA.traceId),
            recorder.traces,
            "Expected unchanged traces after first deactivate"
        )
        assertFalse(recorder.deactivate(), "Second deactivate returned true")
        assertFalse(recorder.isActive, "IsActive true after second deactivate")
        recorder.storeIfActive(TraceTestData.trace3SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId, TraceTestData.trace2SpanA.traceId),
            recorder.traces,
            "Expected unchanged traces after second deactivate"
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testRecordUse() {
        assertEquals(emptyList(), recorder.traces, "Expected no initial traces")
        assertFalse(recorder.isActive, "Recorder started active")
        recorder.record().use {
            assertTrue(recorder.isActive, "rec.isActive is false for expected active recorder")
            recorder.storeIfActive(TraceTestData.trace1SpanA)
            assertEquals(
                listOf(TraceTestData.trace1SpanA.traceId),
                recorder.traces,
                "Expected traces when recording with active recorder"
            )
        }
        assertEquals(listOf(TraceTestData.trace1SpanA.traceId), recorder.traces, "Traces changed by close")
        assertFalse(recorder.isActive, "IsActive true after close")
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testRecordUseAfterActivate() {
        assertEquals(emptyList(), recorder.traces, "Expected no initial traces")
        assertFalse(recorder.isActive, "Recorder started active")
        assertTrue(recorder.activate(), "Activate returned false")
        assertTrue(recorder.isActive, "rec.isActive is false after activate")
        recorder.storeIfActive(TraceTestData.trace1SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId),
            recorder.traces,
            "Expected recording of one trace after activate"
        )
        recorder.record().use {
            assertTrue(recorder.isActive, "rec.isActive is false inside record().use{}")
            recorder.storeIfActive(TraceTestData.trace2SpanA)
            assertEquals(
                listOf(TraceTestData.trace1SpanA.traceId, TraceTestData.trace2SpanA.traceId),
                recorder.traces,
                "Expected recording of two traces in record().use{} after activate"
            )
        }
        assertTrue(recorder.isActive, "rec.isActive is false after record().use{} but still before deactivate")
        recorder.storeIfActive(TraceTestData.trace3SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId, TraceTestData.trace2SpanA.traceId, TraceTestData.trace3SpanA.traceId),
            recorder.traces,
            "Expected recording of three traces after record().use{} but still before deactivate"
        )
        assertTrue(recorder.deactivate(), "First deactivate returned false")
        assertFalse(recorder.isActive, "IsActive true after deactivate")
        recorder.storeIfActive(TraceTestData.trace4SpanA)
        assertEquals(
            listOf(TraceTestData.trace1SpanA.traceId, TraceTestData.trace2SpanA.traceId, TraceTestData.trace3SpanA.traceId),
            recorder.traces,
            "Expected unchanged traces after first deactivate"
        )
    }
}
