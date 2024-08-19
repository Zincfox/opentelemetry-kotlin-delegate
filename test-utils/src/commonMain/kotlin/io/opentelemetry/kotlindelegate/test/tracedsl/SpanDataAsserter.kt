package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.api.common.AttributeKey
import io.opentelemetry.kotlindelegate.api.common.Attributes
import io.opentelemetry.kotlindelegate.api.trace.SpanKind
import io.opentelemetry.kotlindelegate.api.trace.StatusCode
import io.opentelemetry.kotlindelegate.test.SpanData
import io.opentelemetry.kotlindelegate.test.TraceForestRecorder
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.fail

interface SpanDataAsserter<out ChildAsserter : SpanDataAsserter<ChildAsserter>> : SpanDataContainerAsserter<ChildAsserter> {

    val data: SpanData
    val name: String
        get() = data.name

    val startEpochNanos: Long
        get() = data.startEpochNanos
    val endEpochNanos: Long
        get() = data.endEpochNanos

    val durationNanos: Long
        get() = data.endEpochNanos - data.startEpochNanos

    fun assertName(expected: String) {
        assertEquals(expected, data.name, "Name does not match at $path")
    }

    fun assertName(expected: Regex): MatchResult {
        return assertNotNull(expected.matchEntire(data.name), "Regex does not match name at $path")
    }

    fun assertHasParent(expected: Boolean = true) {
        if (expected)
            assertNotNull(data.parentSpanId, "No parent present at $path")
        else
            assertNull(data.parentSpanId, "Expected root span but parent was present at $path: ${data.parentSpanId}")
    }

    fun assertSampled(expected: Boolean = true) {
        assertEquals(
            expected,
            data.spanContext.isSampled(),
            "IsSampled does not match expected value $expected at $path: ${data.spanContext.isSampled()}"
        )
    }

    fun assertKind(expected: SpanKind) {
        assertEquals(expected, data.kind, "SpanKind does not match expected value $expected at $path: ${data.kind}")
    }

    fun assertStatus(expected: StatusCode) {
        if (expected != data.statusCode) {
            if (data.status.description.isNotEmpty())
                throw AssertionError("Expected status code $expected but received status code ${data.statusCode} at $path: ${data.status.description}")
            else
                throw AssertionError("Expected status code $expected but received status code ${data.statusCode} at $path")
        }
    }

    fun assertStatusOneOf(vararg expected: StatusCode) {
        if (data.statusCode !in expected) {
            if (data.status.description.isNotEmpty())
                throw AssertionError("Expected status code $expected but received status code ${data.statusCode} at $path: ${data.status.description}")
            else
                throw AssertionError("Expected status code $expected but received status code ${data.statusCode} at $path")
        }
    }

    fun assertHasEnded(expected: Boolean = true) {
        assertEquals(expected, data.hasEnded, "HasEnded does not match expected value $expected at $path")
    }

    fun <T : Any> assertAttributesEquals(
        key: AttributeKey<T>,
        value: T,
        allowMissing: Boolean = false,
    ) {
        val actual = data.attributes[key]
        if (!allowMissing) {
            assertNotNull(actual, "Attribute $key is missing at $path")
        }
        actual ?: return
        assertEquals(value, actual, "Attribute $key does not match $value at $path")
    }

    fun assertAttributesEquals(
        expected: Attributes,
        allowMissing: Boolean = false,
        allowAdditional: Boolean = false,
    ) {
        expected.forEach { k, v ->
            @Suppress("UNCHECKED_CAST")
            assertAttributesEquals(k as AttributeKey<Any>, v, allowMissing)
        }
        if (!allowAdditional) {
            val expectedKeys = expected.asMap().keys.mapTo(mutableSetOf()) { it.getKey() }
            val additionalEntries = data.attributes.asMap().filterKeys { it.getKey() !in expectedKeys }
            if(additionalEntries.isNotEmpty()) {
                fail("Found additional attributes at $path: $additionalEntries")
            }
        }
    }

    fun assertAttributesPresent(
        key: AttributeKey<*>,
    ) {
        @Suppress("UNCHECKED_CAST")
        assertNotNull(data.attributes[key as AttributeKey<Any>], "Attribute $key is missing at $path")
    }

    class DefaultSpanDataAsserter(
        val recorder: TraceForestRecorder,
        override val data: SpanData,
        parentSpanPath: List<Pair<String, String?>>,
        val rootSpanTraceId: String,
    ) : SpanDataAsserter<DefaultSpanDataAsserter> {

        val spanPath: List<Pair<String, String?>> =
            parentSpanPath + listOf(data.spanId to data.name.takeIf(String::isNotEmpty))

        constructor(recorder: TraceForestRecorder, data: SpanData) : this(
            recorder,
            data,
            emptyList(),
            data.traceId
        )

        override val path: String
            get() {
                return rootSpanTraceId + spanPath.joinToString(separator = "/", prefix = "/") { (spanId, name) ->
                    if (name != null)
                        "$name[$spanId]"
                    else
                        spanId
                }
            }

        override val directChildren: Sequence<DefaultSpanDataAsserter>
            get() {
                return recorder.getSpanChildren(data).asSequence()
                    .map { DefaultSpanDataAsserter(recorder, it, spanPath, rootSpanTraceId) }
            }

        override fun toString(): String {
            return "DefaultSpanDataAsserter(path='$path', data=$data)"
        }
    }
}
