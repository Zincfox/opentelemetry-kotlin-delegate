package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.trace.SpanContext
import io.opentelemetry.kotlindelegate.test.tracedsl.TraceForestAsserter
import kotlin.jvm.JvmName

@OptIn(ExperimentalStdlibApi::class)
interface TraceForestRecorder {

    val traces: List<String>
    fun getTraceSpans(traceId: String): List<SpanData>
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

@OptIn(ExperimentalStdlibApi::class)
inline fun TraceForestRecorder.alsoRecord(recordedBlock: () -> Unit): TraceForestRecorder =
    also { it.record().use { recordedBlock() } }

@OptIn(ExperimentalStdlibApi::class)
@JvmName("alsoRecordNullable")
inline fun TraceForestRecorder?.alsoRecord(recordedBlock: () -> Unit): TraceForestRecorder? {
    return if(this == null) {
        recordedBlock()
        null
    }
    else {
        also { it.record().use { recordedBlock() } }
    }
}

@OptIn(ExperimentalStdlibApi::class)
inline fun <T> TraceForestRecorder.recordAssert(
    recordedBlock: () -> T,
    assertionBlock: TraceForestAsserter.() -> Unit,
): T {
    val result = record().use { recordedBlock() }
    TraceForestAsserter.DefaultTraceForestAsserter(this).assertionBlock()
    return result
}

@OptIn(ExperimentalStdlibApi::class)
@JvmName("recordAssertNullable")
inline fun <T> TraceForestRecorder?.recordAssert(
    recordedBlock: () -> T,
    assertionBlock: TraceForestAsserter.() -> Unit,
): T {
    if(this == null) {
        return recordedBlock()
    }
    val result = record().use { recordedBlock() }
    TraceForestAsserter.DefaultTraceForestAsserter(this).assertionBlock()
    return result
}

inline fun TraceForestRecorder.assert(assertionBlock: TraceForestAsserter.() -> Unit) {
    TraceForestAsserter.DefaultTraceForestAsserter(this).assertionBlock()
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
            children.getOrElse(spanId) {
                if (spanId in spans)
                    return emptyList()
                else
                    throw NoSuchElementException("Span '${spanId}' not found in trace '$traceId'")
            }.map {
                spans.getValue(it)
            }

        fun rootSpans(): List<SpanData> = spans.mapNotNull {
            if (it.key in storedParents)
                return@mapNotNull null
            else
                it.value
        }

        fun allSpans(): Map<String, SpanData> = spans.toMap()

        fun store(span: SpanData) {
            spans[span.spanId] = span
            val storedParent = storedParents[span.spanId]
            if (storedParent != null && storedParent != span.parentSpanId) {
                children.getValue(storedParent).remove(span.spanId)
                storedParents.remove(span.spanId)
            }
            val parentSpanId = span.parentSpanId
            if (!parentSpanId.isNullOrEmpty()) {
                children.getOrPut(parentSpanId) { mutableListOf() }.add(span.spanId)
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

    override fun getTraceSpans(traceId: String): List<SpanData> {
        return _traces.getOrElse(traceId) { throw NoSuchElementException("TraceID '${traceId}' not found") }
            .allSpans().values.toList()
    }

    override fun getTraceRootSpans(traceId: String): List<SpanData> {
        return _traces.getOrElse(traceId) {
            throw NoSuchElementException("TraceID '${traceId}' not found")
        }.rootSpans()
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

    fun storeIfActive(spanData: SpanData) {
        if(isActive) store(spanData)
    }
}
