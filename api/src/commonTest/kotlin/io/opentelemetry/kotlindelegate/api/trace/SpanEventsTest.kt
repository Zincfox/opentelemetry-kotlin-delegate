package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.api.common.AttributeKeyStatic
import io.opentelemetry.kotlindelegate.api.common.AttributesStatic
import io.opentelemetry.kotlindelegate.test.OpenTelemetryTesting
import io.opentelemetry.kotlindelegate.test.assert
import io.opentelemetry.kotlindelegate.utils.startSpan
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SpanEventsTest {

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
    fun defaultEmptyEventsTest() {
        val tracer = GlobalOpenTelemetry.getTracer("defaultEmptyEventsTest")
        val span = tracer.startSpan("span")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertEquals(emptyList(), data.events, "Default event-list is not empty")
                    }
                }
            }
        }
    }

    @Test
    fun recordSimpleEventNameTest() {
        val tracer = GlobalOpenTelemetry.getTracer("recordSimpleEventTest")
        val span = tracer.startSpan("span")
        span.addEvent("simple-event")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertEquals(1, data.events.size, "Expected exactly one event: ${data.events}")
                        assertEquals(
                            "simple-event",
                            data.events.single().name,
                            "Event name is not \"simple-event\": ${data.events.single()}"
                        )
                    }
                }
            }
        }
    }

    @Test
    fun recordSimpleEventTimeTest() {
        val tracer = GlobalOpenTelemetry.getTracer("recordSimpleEventTest")
        val span = tracer.startSpan("span") { setStartTimestampMillis(100L) }
        span.addEventMillis("simple-event", 200L)
        span.endMillis(300L)
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertEquals(1, data.events.size, "Expected exactly one event: ${data.events}")
                        assertEquals(
                            200_000_000L,
                            data.events.single().epochNanos,
                            "Event timestamp is not 200000000ns (200ms): ${data.events.single()}"
                        )
                    }
                }
            }
        }
    }

    @Test
    fun recordEventAttributesTest() {
        val tracer = GlobalOpenTelemetry.getTracer("recordSimpleEventTest")
        val span = tracer.startSpan("span")
        val attributes = AttributesStatic.of(AttributeKeyStatic.stringKey("test"), "value")
        span.addEvent("simple-event", attributes)
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertEquals(1, data.events.size, "Expected exactly one event: ${data.events}")
                        assertEquals(
                            attributes,
                            data.events.single().attributes,
                            "Event attributes is not $attributes: ${data.events.single()}"
                        )
                    }
                }
            }
        }
    }
}
