package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.test.OpenTelemetryTesting
import io.opentelemetry.kotlindelegate.test.assert
import io.opentelemetry.kotlindelegate.utils.startSpan
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SpanKindTest {

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
    fun spanKindDefaultTest() {
        val tracer = GlobalOpenTelemetry.getTracer("spanKindDefaultTest")
        tracer.startSpan("span").end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertKind(SpanKind.INTERNAL)
                    }
                }
            }
        }
    }

    @Test
    fun spanKindInternalTest() {
        val tracer = GlobalOpenTelemetry.getTracer("spanKindInternalTest")
        tracer.startSpan("span") { setSpanKind(SpanKind.INTERNAL) }.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertKind(SpanKind.INTERNAL)
                    }
                }
            }
        }
    }

    @Test
    fun spanKindServerTest() {
        val tracer = GlobalOpenTelemetry.getTracer("spanKindServerTest")
        tracer.startSpan("span") { setSpanKind(SpanKind.SERVER) }.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertKind(SpanKind.SERVER)
                    }
                }
            }
        }
    }

    @Test
    fun spanKindClientTest() {
        val tracer = GlobalOpenTelemetry.getTracer("spanKindClientTest")
        tracer.startSpan("span") { setSpanKind(SpanKind.CLIENT) }.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertKind(SpanKind.CLIENT)
                    }
                }
            }
        }
    }

    @Test
    fun spanKindProducerTest() {
        val tracer = GlobalOpenTelemetry.getTracer("spanKindProducerTest")
        tracer.startSpan("span") { setSpanKind(SpanKind.PRODUCER) }.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertKind(SpanKind.PRODUCER)
                    }
                }
            }
        }
    }

    @Test
    fun spanKindConsumerTest() {
        val tracer = GlobalOpenTelemetry.getTracer("spanKindConsumerTest")
        tracer.startSpan("span") { setSpanKind(SpanKind.CONSUMER) }.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertKind(SpanKind.CONSUMER)
                    }
                }
            }
        }
    }
}
