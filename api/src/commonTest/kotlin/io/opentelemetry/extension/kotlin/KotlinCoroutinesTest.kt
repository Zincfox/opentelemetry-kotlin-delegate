/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 * Adapted from https://github.com/open-telemetry/opentelemetry-java/blob/e34049b74e0ba177c769352ef897c00e6c15f9f9/extensions/kotlin/src/test/kotlin/io/opentelemetry/extension/kotlin/KotlinCoroutinesTest.kt
 */

package io.opentelemetry.extension.kotlin

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.api.OpenTelemetry
import io.opentelemetry.kotlindelegate.api.trace.Span
import io.opentelemetry.kotlindelegate.api.trace.SpanStatic
import io.opentelemetry.kotlindelegate.context.*
import io.opentelemetry.kotlindelegate.test.OpenTelemetryTesting
import kotlinx.coroutines.*


import io.opentelemetry.kotlindelegate.utils.coroutines.asContextElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.*


class KotlinCoroutinesTest {

    companion object {
        private val ANIMAL: ContextKey<String> = ContextKeyStatic.named("animal")
    }
    init {
        OpenTelemetryTesting.defaultSetup()
    }

    @Test
    fun runWithContext() = runTest {
        val context1 = ContextStatic.root().with(ANIMAL, "cat")
        assertNull(ContextStatic.current().get(ANIMAL))
        withContext(Dispatchers.Default + context1.asContextElement()) {
            assertEquals("cat", ContextStatic.current().get(ANIMAL))
            assertEquals(ContextStatic.current(), coroutineContext.getOpenTelemetryContext())

            withContext(context1.with(ANIMAL, "dog").asContextElement()) {
                assertEquals("dog", ContextStatic.current().get(ANIMAL))
            }

            assertEquals("cat", ContextStatic.current().get(ANIMAL))

            async(Dispatchers.Main) {
                // Child coroutine inherits context automatically.
                assertEquals("cat", ContextStatic.current().get(ANIMAL))
            }.await()

            coroutineScope {
                // Child coroutine inherits context automatically.
                assertEquals("cat", ContextStatic.current().get(ANIMAL))
            }

            CoroutineScope(Dispatchers.Main).async {
                // Non-child coroutine does not inherit context automatically.
                assertNull(ContextStatic.current().get(ANIMAL))
            }.await()
        }
    }

    @Test
    fun runWithSpan() = runTest {
        val span: Span = GlobalOpenTelemetry.getTracer("test").spanBuilder("test")
            .startSpan()
        assertEquals(SpanStatic.getInvalid(), SpanStatic.current())
        withContext(Dispatchers.Main + span.asContextElement()) {
            assertEquals(span, SpanStatic.current())
        }
    }

    @Test
    fun getOpenTelemetryContextOutsideOfContext() = runTest {
        withContext(Dispatchers.Default) {
            assertEquals(ContextStatic.root(),coroutineContext.getOpenTelemetryContext())
        }
    }

    @Test
    @DelicateCoroutinesApi
    fun stressTest() = runTest {
        val context1 = ContextStatic.root().with(ANIMAL, "cat")
        withContext(context1.asContextElement()) {
            assertEquals("cat", ContextStatic.current().get(ANIMAL))
            delay(10)
            assertEquals("cat", ContextStatic.current().get(ANIMAL))
            for (i in 0 until 100) {
                launch {
                    assertEquals("cat", ContextStatic.current().get(ANIMAL))
                    withContext(context1.with(ANIMAL, "dog").asContextElement()) {
                        assertEquals("dog", ContextStatic.current().get(ANIMAL))
                        delay(10)
                        assertEquals("dog", ContextStatic.current().get(ANIMAL))
                    }
                }
                launch {
                    assertEquals("cat", ContextStatic.current().get(ANIMAL))
                    withContext(context1.with(ANIMAL, "koala").asContextElement()) {
                        assertEquals("koala", ContextStatic.current().get(ANIMAL))
                        delay(10)
                        assertEquals("koala", ContextStatic.current().get(ANIMAL))
                    }
                }
            }
        }
    }
}
