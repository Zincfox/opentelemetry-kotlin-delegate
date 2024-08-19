package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.test.OpenTelemetryTesting
import io.opentelemetry.kotlindelegate.test.assert
import io.opentelemetry.kotlindelegate.utils.startSpan
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SpanStatusTest {

    val recorder = OpenTelemetryTesting.defaultSetup()

    @BeforeTest
    fun setup() {
        recorder.clear()
        recorder.activate()
    }

    @AfterTest
    fun teardown() {
        recorder.deactivate()
    }

    @Test
    fun defaultStatusTest() {
        val tracer = GlobalOpenTelemetry.getTracer("defaultStatusTest")
        val span = tracer.startSpan("span")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertStatus(StatusCode.UNSET)
                    }
                }
            }
        }
    }
    @Test
    fun unsetStatusTest() {
        val tracer = GlobalOpenTelemetry.getTracer("unsetStatusTest")
        val span = tracer.startSpan("span")
        span.setStatus(StatusCode.UNSET)
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertStatus(StatusCode.UNSET)
                    }
                }
            }
        }
    }
    @Test
    fun okStatusTest() {
        val tracer = GlobalOpenTelemetry.getTracer("okStatusTest")
        val span = tracer.startSpan("span")
        span.setStatus(StatusCode.OK)
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertStatus(StatusCode.OK)
                    }
                }
            }
        }
    }
    @Test
    fun errorStatusTest() {
        val tracer = GlobalOpenTelemetry.getTracer("errorStatusTest")
        val span = tracer.startSpan("span")
        span.setStatus(StatusCode.ERROR)
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertStatus(StatusCode.ERROR)
                    }
                }
            }
        }
    }
    @Test
    fun unsetStatusDescriptionTest() {
        val tracer = GlobalOpenTelemetry.getTracer("unsetStatusDescriptionTest")
        val span = tracer.startSpan("span")
        span.setStatus(StatusCode.UNSET, "Unset description")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertStatus(StatusCode.UNSET)
                        assertEquals("Unset description", data.status.description)
                    }
                }
            }
        }
    }

    @Test
    fun okStatusDescriptionTest() {
        val tracer = GlobalOpenTelemetry.getTracer("okStatusDescriptionTest")
        val span = tracer.startSpan("span")
        span.setStatus(StatusCode.OK, "Ok description")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertStatus(StatusCode.OK)
                        assertEquals("Ok description", data.status.description)
                    }
                }
            }
        }
    }
    @Test
    fun errorStatusDescriptionTest() {
        val tracer = GlobalOpenTelemetry.getTracer("errorStatusDescriptionTest")
        val span = tracer.startSpan("span")
        span.setStatus(StatusCode.ERROR, "Error description")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertStatus(StatusCode.ERROR)
                        assertEquals("Error description", data.status.description)
                    }
                }
            }
        }
    }

    @Test
    fun statusChangeTest() {
        val tracer = GlobalOpenTelemetry.getTracer("statusChangeTest")
        for (firstCode in StatusCode.entries) {
            //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
            if(firstCode == StatusCode.OK) continue
            for (secondCode in StatusCode.entries) {
                val span = tracer.startSpan("Span_${firstCode.name}_${secondCode.name}")
                span.setStatus(firstCode)
                span.setStatus(secondCode)
                span.end()
            }
        }
        var nextTraceIndex = 0
        recorder.assert {
            for (firstCode in StatusCode.entries) {
                //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
                if(firstCode == StatusCode.OK) continue
                for (secondCode in StatusCode.entries) {
                    traces[nextTraceIndex++].apply {
                        singleDirectChild {
                            assertName("Span_${firstCode.name}_${secondCode.name}")
                            assertNoChildren()
                            assertStatus(secondCode)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun statusChangeDescriptionAddTest() {
        val tracer = GlobalOpenTelemetry.getTracer("statusChangeDescriptionAddTest")
        for (firstCode in StatusCode.entries) {
            //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
            if(firstCode == StatusCode.OK) continue
            for (secondCode in StatusCode.entries) {
                val span = tracer.startSpan("Span_${firstCode.name}_${secondCode.name}")
                span.setStatus(firstCode)
                span.setStatus(secondCode, "${secondCode.name} description")
                span.end()
            }
        }
        var nextTraceIndex = 0
        recorder.assert {
            for (firstCode in StatusCode.entries) {
                //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
                if(firstCode == StatusCode.OK) continue
                for (secondCode in StatusCode.entries) {
                    traces[nextTraceIndex++].apply {
                        singleDirectChild {
                            assertName("Span_${firstCode.name}_${secondCode.name}")
                            assertNoChildren()
                            assertEquals("${secondCode.name} description", data.status.description)
                            assertStatus(secondCode)
                        }
                    }
                }
            }
        }
    }
    @Test
    fun statusChangeDescriptionRemoveTest() {
        val tracer = GlobalOpenTelemetry.getTracer("statusChangeDescriptionRemoveTest")
        for (firstCode in StatusCode.entries) {
            //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
            if(firstCode == StatusCode.OK) continue
            for (secondCode in StatusCode.entries) {
                val span = tracer.startSpan("Span_${firstCode.name}_${secondCode.name}")
                span.setStatus(firstCode, "${firstCode.name} description")
                span.setStatus(secondCode)
                span.end()
            }
        }
        var nextTraceIndex = 0
        recorder.assert {
            for (firstCode in StatusCode.entries) {
                //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
                if(firstCode == StatusCode.OK) continue
                for (secondCode in StatusCode.entries) {
                    traces[nextTraceIndex++].apply {
                        singleDirectChild {
                            assertName("Span_${firstCode.name}_${secondCode.name}")
                            assertNoChildren()
                            assertEquals("", data.status.description)
                            assertStatus(secondCode)
                        }
                    }
                }
            }
        }
    }
    @Test
    fun statusChangeDescriptionUpdateTest() {
        val tracer = GlobalOpenTelemetry.getTracer("statusChangeDescriptionUpdateTest")
        for (firstCode in StatusCode.entries) {
            //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
            if(firstCode == StatusCode.OK) continue
            for (secondCode in StatusCode.entries) {
                val span = tracer.startSpan("Span_${firstCode.name}_${secondCode.name}")
                span.setStatus(firstCode, "${firstCode.name} description")
                span.setStatus(secondCode, "${secondCode.name} description")
                span.end()
            }
        }
        var nextTraceIndex = 0
        recorder.assert {
            for (firstCode in StatusCode.entries) {
                //different behaviour depending on platform: JVM ignores status modification after OK, JS allows them.
                if(firstCode == StatusCode.OK) continue
                for (secondCode in StatusCode.entries) {
                    traces[nextTraceIndex++].apply {
                        singleDirectChild {
                            assertName("Span_${firstCode.name}_${secondCode.name}")
                            assertNoChildren()
                            assertEquals("${secondCode.name} description", data.status.description)
                            assertStatus(secondCode)
                        }
                    }
                }
            }
        }
    }
}
