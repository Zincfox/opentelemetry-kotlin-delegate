@file:Suppress("MemberVisibilityCanBePrivate")

package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.trace.SpanContextStatic
import io.opentelemetry.kotlindelegate.api.trace.TraceFlagsStatic
import io.opentelemetry.kotlindelegate.api.trace.TraceStateStatic
import kotlin.test.*

class AbstractTraceForestRecorderActiveTest {

    lateinit var recorder: AbstractTraceForestRecorder

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
        @Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
        var didRun = false
        recorder.record().use {
            assertTrue(recorder.isActive, "rec.isActive is false for expected active recorder")
            recorder.storeIfActive(TraceTestData.trace1SpanA)
            assertEquals(
                listOf(TraceTestData.trace1SpanA.traceId),
                recorder.traces,
                "Expected traces when recording with active recorder"
            )
            didRun = true
        }
        assertTrue(didRun, "Recorder.record().use{} did not run block!")
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
        @Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
        var didRun = false
        recorder.record().use {
            assertTrue(recorder.isActive, "rec.isActive is false inside record().use{}")
            recorder.storeIfActive(TraceTestData.trace2SpanA)
            assertEquals(
                listOf(TraceTestData.trace1SpanA.traceId, TraceTestData.trace2SpanA.traceId),
                recorder.traces,
                "Expected recording of two traces in record().use{} after activate"
            )
            didRun = true
        }
        assertTrue(didRun, "Recorder.record().use{} did not run block!")
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
