package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.trace.SpanContext

@OptIn(ExperimentalStdlibApi::class)
interface TraceForestRecorder {

    val traces: List<String>
    fun getTraceRootSpans(traceId: String): List<SpanData>
    fun getSpanChildren(traceId: String, spanId: String): List<SpanData>
    fun getSpanChildren(spanContext: SpanContext): List<SpanData> =
        getSpanChildren(spanContext.getTraceId(), spanContext.getSpanId())

    fun getSpanChildren(span: SpanData): List<SpanData> = getSpanChildren(span.spanContext)
    val isActive: Boolean
    fun activate(): Boolean
    fun deactivate(): Boolean
    fun clear()
    fun record(): AutoCloseable {
        return if (activate()) {
            object : AutoCloseable {
                override fun close() {
                    deactivate()
                }
            }
        } else {
            object : AutoCloseable {
                override fun close() {}
            }
        }
    }
}

operator fun TraceForestRecorder.get(traceId: String): List<SpanData> = getTraceRootSpans(traceId)
operator fun TraceForestRecorder.get(spanContext: SpanContext): List<SpanData> = getSpanChildren(spanContext)
operator fun TraceForestRecorder.get(span: SpanData): List<SpanData> = getSpanChildren(span)

abstract class AbstractTraceForestRecorder(startActive: Boolean = false) : TraceForestRecorder {

    protected val _traces: MutableMap<String, TraceTree> = mutableMapOf()

    class TraceTree(
        val traceId: String,
    ) {

        private val spans: MutableMap<String, SpanData> = mutableMapOf()
        private val children: MutableMap<String, MutableList<String>> = mutableMapOf()
        private val storedParents: MutableMap<String, String> = mutableMapOf()

        fun childSpans(spanId: String): List<SpanData> =
            children.getOrElse(spanId) { throw NoSuchElementException("TraceID '${traceId}' not found") }.map {
                spans.getValue(it)
            }

        fun rootSpans(): List<SpanData> = spans.mapNotNull {
            if(it.key in storedParents)
                return@mapNotNull null
            else
                it.value
        }
        fun allSpans(): Map<String, SpanData> = spans.toMap()

        fun store(span: SpanData) {
            spans[span.spanId] = span
            val storedParent = storedParents[span.spanId]
            if(storedParent != null && storedParent != span.parentSpanId) {
                children.getValue(storedParent).remove(span.spanId)
                storedParents.remove(span.spanId)
            }
            val parentSpanId = span.parentSpanId
            if(parentSpanId != null){
                children.getOrPut(span.spanId) { mutableListOf() }.add(span.spanId)
                storedParents[span.spanId] = parentSpanId
            }
        }
    }

    override val traces: List<String>
        get() = _traces.keys.toList()

    override fun getSpanChildren(traceId: String, spanId: String): List<SpanData> {
        return _traces.getOrElse(traceId) { throw NoSuchElementException("TraceID '${traceId}' not found") }
            .childSpans(spanId)
    }

    override fun getTraceRootSpans(traceId: String): List<SpanData> {
        return _traces.getOrElse(traceId) { throw NoSuchElementException("TraceID '${traceId}' not found") }
            .rootSpans()
    }

    override var isActive: Boolean = startActive
        protected set

    override fun activate(): Boolean {
        return !(isActive.also { isActive = true })
    }

    override fun deactivate(): Boolean {
        return isActive.also { isActive = false }
    }

    override fun clear() {
        _traces.clear()
    }

    protected open fun store(spanData: SpanData) {
        _traces.getOrPut(spanData.traceId) {
            TraceTree(spanData.traceId)
        }.store(spanData)
    }
}
