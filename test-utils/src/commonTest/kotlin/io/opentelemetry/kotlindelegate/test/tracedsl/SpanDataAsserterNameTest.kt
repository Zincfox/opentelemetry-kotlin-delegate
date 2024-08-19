package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.test.*
import kotlin.test.*

class SpanDataAsserterNameTest {

    lateinit var recorder: AbstractTraceForestRecorder

    @BeforeTest
    fun setup() {
        recorder = TraceForestRecorderTestImpl(startActive = true)
        TraceTestData.allSpans.values.flatten().forEach(recorder::storeIfActive)
        recorder.deactivate()
        TraceTestData.allSpans.forEach { (trace: String, dataList: List<SpanData>) ->
            assertContentEquals(dataList, recorder.getTraceSpans(trace))
        }
    }

    @Test
    fun testAssertNameStringPasses() {
        assertNameStringPasses(SpanDataAsserter.DefaultSpanDataAsserter(recorder, TraceTestData.trace1SpanA))
    }

    fun assertNameStringPasses(spanAsserter: SpanDataAsserter<*>) {
        try {
            spanAsserter.assertName(spanAsserter.data.name)
        } catch (e: AssertionError) {
            fail("assertName(String) does not pass for case $spanAsserter", cause = e)
        }
    }

    @Test
    fun testAssertNameStringThrows() {
        assertNameStringThrows(SpanDataAsserter.DefaultSpanDataAsserter(recorder, TraceTestData.trace1SpanA))
    }

    fun assertNameStringThrows(spanAsserter: SpanDataAsserter<*>) {
        val assertionMessage =
            assertFailsWith<AssertionError>("assertName(String) did not fail for case $spanAsserter") {
                spanAsserter.assertName(spanAsserter.data.name + "-error")
            }.message
        assertNotNull(assertionMessage, "Assertion message is null for assertName(String) (case $spanAsserter)")
        assertTrue(
            assertionMessage.startsWith("Name does not match at "),
            "Assertion message does not match for assertName(String) (case $spanAsserter): $assertionMessage"
        )
    }

    @Test
    fun testAssertNameRegexPasses() {
        assertNameRegexPasses(SpanDataAsserter.DefaultSpanDataAsserter(recorder, TraceTestData.trace1SpanA))
    }

    fun assertNameRegexPasses(spanAsserter: SpanDataAsserter<*>) {
        try {
            spanAsserter.assertName(Regex.fromLiteral(spanAsserter.data.name))
        } catch (e: AssertionError) {
            fail("assertName(Regex) does not pass for case $spanAsserter", cause = e)
        }
    }

    @Test
    fun testAssertNameRegexThrows() {
        assertNameRegexThrows(SpanDataAsserter.DefaultSpanDataAsserter(recorder, TraceTestData.trace1SpanA))
        assertNameRegexEmptyThrows(SpanDataAsserter.DefaultSpanDataAsserter(recorder, TraceTestData.trace1SpanA))
    }

    fun assertNameRegexThrows(spanAsserter: SpanDataAsserter<*>) {
        val assertionMessage =
            assertFailsWith<AssertionError>("assertName(Regex) did not fail for case $spanAsserter") {
                spanAsserter.assertName(Regex.fromLiteral(spanAsserter.data.name + "-error"))
            }.message
        assertNotNull(assertionMessage, "Assertion message is null for assertName(Regex) (case $spanAsserter)")
        assertTrue(
            assertionMessage.startsWith("Regex does not match name at "),
            "Assertion message does not match for assertName(Regex) (case $spanAsserter): $assertionMessage"
        )
    }

    fun assertNameRegexEmptyThrows(spanAsserter: SpanDataAsserter<*>) {
        val assertionMessage =
            assertFailsWith<AssertionError>("assertName(emptyRegex) did not fail for case $spanAsserter") {
                spanAsserter.assertName(Regex(""))
            }.message
        assertNotNull(assertionMessage, "Assertion message is null for assertName(emptyRegex) (case $spanAsserter)")
        assertTrue(
            assertionMessage.startsWith("Regex does not match name at "),
            "Assertion message does not match for assertName(emptyRegex) (case $spanAsserter): $assertionMessage"
        )
    }
}
