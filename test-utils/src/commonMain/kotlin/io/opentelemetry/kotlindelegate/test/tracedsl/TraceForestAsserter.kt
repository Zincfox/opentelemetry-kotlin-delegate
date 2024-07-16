package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.test.TraceForestRecorder
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OtelTraceDslMarker
interface TraceForestAsserter {

    val traces: List<TraceAsserter>
    val recorder: TraceForestRecorder

    fun firstTrace(block: TraceAsserter.() -> Unit) {
        assertNotEquals(emptyList(), traces, "No traces found")
        traces.first().block()
    }
    fun lastTrace(block: TraceAsserter.() -> Unit) {
        assertNotEquals(emptyList(), traces, "No traces found")
        traces.last().block()
    }
    fun singleTrace(block: TraceAsserter.() -> Unit) {
        assertNotEquals(emptyList(), traces, "No traces found")
        assertEquals(1, traces.size, "Found more than one single trace")
        traces.single().block()
    }
    fun allTraces(block: TraceAsserter.() -> Unit) {
        traces.forEach(block)
    }

    operator fun Int.invoke(block: TraceAsserter.() -> Unit) {
        val index = if(this < 0) traces.size - this else this
        assertContains(traces.indices, index, "Trace with index $this ($index) not found")
        traces[index].block()
    }


    class DefaultTraceForestAsserter(override val recorder: TraceForestRecorder) : TraceForestAsserter {

        override val traces: List<TraceAsserter>
            get() = recorder.traces.map { TraceAsserter.DefaultTraceAsserter(recorder, it) }
    }
}
