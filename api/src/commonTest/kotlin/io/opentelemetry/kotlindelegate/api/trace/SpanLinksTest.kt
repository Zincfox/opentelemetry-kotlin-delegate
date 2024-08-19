package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.api.common.AttributeKeyStatic
import io.opentelemetry.kotlindelegate.api.common.AttributesStatic
import io.opentelemetry.kotlindelegate.context.ContextStatic
import io.opentelemetry.kotlindelegate.test.OpenTelemetryTesting
import io.opentelemetry.kotlindelegate.test.SpanData
import io.opentelemetry.kotlindelegate.test.assert
import io.opentelemetry.kotlindelegate.utils.startSpan
import kotlin.test.*

class SpanLinksTest {

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
    fun defaultEmptyLinksTest() {
        val tracer = GlobalOpenTelemetry.getTracer("defaultEmptyLinksTest")
        val span = tracer.startSpan("span")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertEquals(emptyList(), data.links, "Default link-list is not empty")
                    }
                }
            }
        }
    }

    @Test
    fun directParentLinkTest() {
        val tracer = GlobalOpenTelemetry.getTracer("directParentLinkTest")
        val parent = tracer.startSpan("parent")
        val attributes = AttributesStatic.of(AttributeKeyStatic.stringKey("test"), "value")
        val child = tracer.startSpan("child") {
            setParent(parent.storeInContext(ContextStatic.root()))
            addLink(parent.getSpanContext(), attributes)
        }
        child.end()
        parent.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "parent" {
                        "child" {
                            assertEquals(
                                listOf(SpanData.LinkData(parent.getSpanContext(), attributes)),
                                data.links
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun completedParentLinkTest() {
        val tracer = GlobalOpenTelemetry.getTracer("completedParentLinkTest")
        val parent = tracer.startSpan("parent") { setStartTimestampMillis(100L) }
        parent.endMillis(200L)
        val attributes = AttributesStatic.of(AttributeKeyStatic.stringKey("test"), "value")
        val child = tracer.startSpan("child") {
            setParent(parent.storeInContext(ContextStatic.root()))
            addLink(parent.getSpanContext(), attributes)
            setStartTimestampMillis(300L)
        }
        child.endMillis(400L)
        recorder.assert {
            singleTrace {
                ordered {
                    "parent" {
                        "child" {
                            assertEquals(
                                listOf(SpanData.LinkData(parent.getSpanContext(), attributes)),
                                data.links
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun directNeighbourLinkTest() {
        val tracer = GlobalOpenTelemetry.getTracer("directNeighbourLinkTest")
        val parent = tracer.startSpan("parent")
        val neighbour = tracer.startSpan("neighbour") {
            setParent(parent.storeInContext(ContextStatic.root()))
        }
        val attributes = AttributesStatic.of(AttributeKeyStatic.stringKey("test"), "value")
        val child = tracer.startSpan("child") {
            setParent(parent.storeInContext(ContextStatic.root()))
            addLink(neighbour.getSpanContext(), attributes)
        }
        neighbour.end()
        child.end()
        parent.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "parent" {
                        "neighbour" {
                            assertEquals(emptyList(), data.links)
                        }
                        "child" {
                            assertEquals(
                                listOf(SpanData.LinkData(neighbour.getSpanContext(), attributes)),
                                data.links
                            )
                        }
                    }
                }
            }
        }
    }


    @Test
    fun runningCrossTraceLinkTest() {
        val tracer = GlobalOpenTelemetry.getTracer("runningCrossTraceLinkTest")
        val span1 = tracer.startSpan("span1")
        val attributes = AttributesStatic.of(AttributeKeyStatic.stringKey("test"), "value")
        val span2 = tracer.startSpan("span2") {
            addLink(span1.getSpanContext(), attributes)
        }
        span1.end()
        span2.end()
        recorder.assert {
            assertEquals(2, traces.size, "Wrong number of traces recorded")
            0 {
                ordered {
                    "span1" {
                        assertEquals(emptyList(), data.links)
                    }
                }
            }
            1 {
                ordered {
                    "span2" {
                        assertEquals(listOf(SpanData.LinkData(span1.getSpanContext(), attributes)), data.links)
                    }
                }
            }
        }
    }

    @Test
    fun completedCrossTraceLinkTest() {
        val tracer = GlobalOpenTelemetry.getTracer("completedCrossTraceLinkTest")
        val span1 = tracer.startSpan("span1") { setStartTimestampMillis(100L) }
        span1.endMillis(200L)
        val attributes = AttributesStatic.of(AttributeKeyStatic.stringKey("test"), "value")
        val span2 = tracer.startSpan("span2") {
            addLink(span1.getSpanContext(), attributes)
            setStartTimestampMillis(300L)
        }
        span2.endMillis(400L)
        recorder.assert {
            assertEquals(2, traces.size, "Wrong number of traces recorded")
            0 {
                ordered {
                    "span1" {
                        assertEquals(emptyList(), data.links)
                    }
                }
            }
            1 {
                ordered {
                    "span2" {
                        assertEquals(listOf(SpanData.LinkData(span1.getSpanContext(), attributes)), data.links)
                    }
                }
            }
        }
    }
}
