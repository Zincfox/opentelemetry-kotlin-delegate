package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.test.SpanMatchingStrategy
import io.opentelemetry.kotlindelegate.test.TraceForestRecorder

interface TraceAsserter : SpanDataContainerAsserter<SpanDataAsserter<*>> {

    val traceId: String
    override val path: String
        get() = "'$traceId'"

    class DefaultTraceAsserter(
        val recorder: TraceForestRecorder,
        override val traceId: String,
    ) : TraceAsserter {

        override val directChildren: Sequence<SpanDataAsserter<*>>
            get() = recorder.getTraceRootSpans(traceId).asSequence()
                .map { SpanDataAsserter.DefaultSpanDataAsserter(recorder, it) }

        override fun toString(): String {
            return "DefaultTraceAsserter(traceId=$traceId)"
        }
    }
}
