package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.context.ContextStatic
import io.opentelemetry.kotlindelegate.context.runWithActive
import io.opentelemetry.kotlindelegate.context.runWithActiveSuspend
import io.opentelemetry.kotlindelegate.test.OpenTelemetryTesting
import io.opentelemetry.kotlindelegate.test.assert
import io.opentelemetry.kotlindelegate.utils.startSpan
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SpanNestingTest {

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
    fun simpleRecorderTest() {
        val span = GlobalOpenTelemetry.getTracer("simpleRecorderTest")
            .spanBuilder("TestSpan")
            .startSpan()
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "TestSpan" {
                        assertStatus(StatusCode.UNSET)
                        assertNoChildren()
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }

    @Test
    fun multipleTracesTest() {
        val builder1 = GlobalOpenTelemetry.getTracer("multipleTracesTest1")
        val span1 = builder1
            .spanBuilder("TestSpan1")
            .startSpan()
        val span2 = builder1
            .spanBuilder("TestSpan2")
            .startSpan()
        span2.end()
        span1.end()
        val span3 = GlobalOpenTelemetry.getTracer("multipleTracesTest2")
            .spanBuilder("TestSpan3")
            .startSpan()
        span3.end()
        recorder.assert {
            assertEquals(3, traces.size, "Unexpected trace count")
            traces[0].ordered {
                "TestSpan2" {
                    assertStatus(StatusCode.UNSET)
                    assertNoChildren()
                    assertHasEnded(true)
                    assertKind(SpanKind.INTERNAL)
                    assertHasParent(false)
                }
            }
            traces[1].ordered {
                "TestSpan1" {
                    assertStatus(StatusCode.UNSET)
                    assertNoChildren()
                    assertHasEnded(true)
                    assertKind(SpanKind.INTERNAL)
                    assertHasParent(false)
                }
            }
            traces[2].ordered {
                "TestSpan3" {
                    assertStatus(StatusCode.UNSET)
                    assertNoChildren()
                    assertHasEnded(true)
                    assertKind(SpanKind.INTERNAL)
                    assertHasParent(false)
                }
            }
        }
    }

    @Test
    fun nestedTracesExplicitParentTest() {
        val builder = GlobalOpenTelemetry.getTracer("nestedTracesExplicitParentTest")
        val outerSpan = builder.startSpan("outer")
        val innerSpan = builder.startSpan("inner") { setParent(outerSpan.storeInContext(ContextStatic.root())) }
        innerSpan.end()
        outerSpan.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            assertStatus(StatusCode.UNSET)
                            assertNoChildren()
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }

    @Test
    fun nestedTracesImplicitParentTest() {
        val builder = GlobalOpenTelemetry.getTracer("nestedTracesImplicitParentTest")
        val outerSpan = builder.startSpan("outer")
        var runCounter = 0
        val innerSpan = outerSpan.runWithActive {
            runCounter++
            builder.startSpan("inner").apply { end() }
        }
        assertEquals(1, runCounter, "runWithActive{} did not execute its block exactly once")
        outerSpan.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            assertStatus(StatusCode.UNSET)
                            assertNoChildren()
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }

    @Test
    fun nestedTracesImplicitSuspendParentTest() = runTest {
        val builder = GlobalOpenTelemetry.getTracer("nestedTracesImplicitSuspendParentTest")
        val outerSpan = builder.startSpan("outer")
        var runCounter = 0
        val innerSpan = outerSpan.runWithActiveSuspend {
            runCounter++
            builder.startSpan("inner").apply { end() }
        }
        assertEquals(1, runCounter, "runWithActiveSuspend{} did not run its block exactly once")
        outerSpan.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            assertStatus(StatusCode.UNSET)
                            assertNoChildren()
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }

    @Test
    fun deepNestedTracesExplicitParentTest() {
        val builder = GlobalOpenTelemetry.getTracer("deepNestedTracesExplicitParentTest")
        val outerSpan = builder.startSpan("outer")
        val innerSpan = builder.startSpan("inner") { setParent(outerSpan.storeInContext(ContextStatic.root())) }
        val deepSpan =
            builder.startSpan("deep") { setParent(innerSpan.storeInContext(outerSpan.storeInContext(ContextStatic.root()))) }
        deepSpan.end()
        innerSpan.end()
        outerSpan.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            "deep" {
                                assertStatus(StatusCode.UNSET)
                                assertNoChildren()
                                assertHasEnded(true)
                                assertKind(SpanKind.INTERNAL)
                                assertHasParent(true)
                            }
                            assertStatus(StatusCode.UNSET)
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }

    @Test
    fun deepNestedTracesImplicitParentTest() {
        val builder = GlobalOpenTelemetry.getTracer("deepNestedTracesImplicitParentTest")
        val outerSpan = builder.startSpan("outer")
        var innerRunCounter = 0
        var innerSpan: Span
        var deepRunCounter = 0
        var deepSpan: Span
        outerSpan.runWithActive {
            innerRunCounter++
            innerSpan = builder.startSpan("inner")
            innerSpan.runWithActive {
                deepRunCounter++
                deepSpan = builder.startSpan("deep")
                deepSpan.end()
            }
            innerSpan.end()
        }
        assertEquals(
            1,
            innerRunCounter,
            "outer runWithActive{} (for inner span) did not execute its block exactly once"
        )
        assertEquals(1, deepRunCounter, "inner runWithActive{} (for deep span) did not execute its block exactly once")
        outerSpan.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            "deep" {
                                assertStatus(StatusCode.UNSET)
                                assertNoChildren()
                                assertHasEnded(true)
                                assertKind(SpanKind.INTERNAL)
                                assertHasParent(true)
                            }
                            assertStatus(StatusCode.UNSET)
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }

    @Test
    fun deepNestedTracesImplicitSuspendParentTest() = runTest {
        val builder = GlobalOpenTelemetry.getTracer("deepNestedTracesImplicitParentTest")
        val outerSpan = builder.startSpan("outer")
        var innerRunCounter = 0
        var innerSpan: Span
        var deepRunCounter = 0
        var deepSpan: Span
        outerSpan.runWithActiveSuspend {
            innerRunCounter++
            innerSpan = builder.startSpan("inner")
            innerSpan.runWithActiveSuspend {
                deepRunCounter++
                deepSpan = builder.startSpan("deep")
                deepSpan.end()
            }
            innerSpan.end()
        }
        assertEquals(
            1,
            innerRunCounter,
            "outer runWithActiveSuspend{} (for inner span) did not execute its block exactly once"
        )
        assertEquals(
            1,
            deepRunCounter,
            "inner runWithActiveSuspend{} (for deep span) did not execute its block exactly once"
        )
        outerSpan.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            "deep" {
                                assertStatus(StatusCode.UNSET)
                                assertNoChildren()
                                assertHasEnded(true)
                                assertKind(SpanKind.INTERNAL)
                                assertHasParent(true)
                            }
                            assertStatus(StatusCode.UNSET)
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }

    @Test
    fun explicitTimestampsTest() {
        val builder = GlobalOpenTelemetry.getTracer("explicitTimestampsTest")
        val span = builder.startSpan("span") {setStartTimestampMillis(100L)}
        span.endMillis(200L)
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertEquals(100_000_000L, startEpochNanos, "Implicit start timestamp does not match")
                        assertEquals(200_000_000L, endEpochNanos, "Implicit end timestamp does not match")
                        assertEquals(100_000_000L, data.startEpochNanos, "Data start timestamp does not match")
                        assertEquals(200_000_000L, data.endEpochNanos, "Data end timestamp does not match")
                        assertEquals(100_000_000L, durationNanos, "Duration does not match")
                        assertHasEnded(true)
                    }
                }
            }
        }
    }

    @Test
    fun outOfOrderChildrenTest() {

        val builder = GlobalOpenTelemetry.getTracer("outOfOrderChildrenTest")
        val outerSpan = builder.startSpan("outer") {
            setStartTimestampMillis(100L)
        }
        lateinit var innerSpan: Span
        lateinit var deepSpan: Span
        outerSpan.runWithActive {
            innerSpan = builder.startSpan("inner") { setStartTimestampMillis(200L) }
            innerSpan.runWithActive {
                deepSpan = builder.startSpan("deep") { setStartTimestampMillis(300L) }
            }
        }
        outerSpan.endMillis(400L)
        innerSpan.endMillis(500L)
        deepSpan.endMillis(600L)
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            "deep" {
                                assertEquals(300_000_000L, startEpochNanos)
                                assertEquals(600_000_000L, endEpochNanos)
                                assertStatus(StatusCode.UNSET)
                                assertNoChildren()
                                assertHasEnded(true)
                                assertKind(SpanKind.INTERNAL)
                                assertHasParent(true)
                            }
                            assertEquals(200_000_000L, startEpochNanos)
                            assertEquals(500_000_000L, endEpochNanos)
                            assertStatus(StatusCode.UNSET)
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertEquals(100_000_000L, startEpochNanos)
                        assertEquals(400_000_000L, endEpochNanos)
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)

                    }
                }
            }
        }
    }

    @Test
    fun outOfOrderChildrenSuspendTest() = runTest {
        val builder = GlobalOpenTelemetry.getTracer("outOfOrderChildrenSuspendTest")
        val outerSpan = builder.startSpan("outer") {
            setStartTimestampMillis(100L)
        }
        lateinit var innerSpan: Span
        lateinit var deepSpan: Span
        outerSpan.runWithActiveSuspend {
            innerSpan = builder.startSpan("inner") { setStartTimestampMillis(200L) }
            innerSpan.runWithActiveSuspend {
                deepSpan = builder.startSpan("deep") { setStartTimestampMillis(300L) }
            }
        }
        outerSpan.endMillis(400L)
        innerSpan.endMillis(500L)
        deepSpan.endMillis(600L)
        recorder.assert {
            singleTrace {
                ordered {
                    "outer" {
                        "inner" {
                            "deep" {
                                assertEquals(300_000_000L, startEpochNanos)
                                assertEquals(600_000_000L, endEpochNanos)
                                assertStatus(StatusCode.UNSET)
                                assertNoChildren()
                                assertHasEnded(true)
                                assertKind(SpanKind.INTERNAL)
                                assertHasParent(true)
                            }
                            assertEquals(200_000_000L, startEpochNanos)
                            assertEquals(500_000_000L, endEpochNanos)
                            assertStatus(StatusCode.UNSET)
                            assertHasEnded(true)
                            assertKind(SpanKind.INTERNAL)
                            assertHasParent(true)
                        }
                        assertEquals(100_000_000L, startEpochNanos)
                        assertEquals(400_000_000L, endEpochNanos)
                        assertStatus(StatusCode.UNSET)
                        assertHasEnded(true)
                        assertKind(SpanKind.INTERNAL)
                        assertHasParent(false)
                    }
                }
            }
        }
    }
}
