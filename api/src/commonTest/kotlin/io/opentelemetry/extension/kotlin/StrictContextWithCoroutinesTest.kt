/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 * Adapted from https://github.com/open-telemetry/opentelemetry-java/blob/e34049b74e0ba177c769352ef897c00e6c15f9f9/extensions/kotlin/src/testStrictContext/kotlin/io/opentelemetry/extension/kotlin/StrictContextWithCoroutinesTest.kt
 */

package io.opentelemetry.extension.kotlin

import io.opentelemetry.kotlindelegate.context.ContextKey
import io.opentelemetry.kotlindelegate.context.ContextKeyStatic
import io.opentelemetry.kotlindelegate.context.ContextStatic
import io.opentelemetry.kotlindelegate.context.runWithActive
import io.opentelemetry.kotlindelegate.utils.coroutines.asContextElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.*

class StrictContextWithCoroutinesTest {

    companion object {
        private val ANIMAL: ContextKey<String> = ContextKeyStatic.named("animal")
    }

    @Test
    fun noMakeCurrentSucceeds() = runTest {
        val context1 = ContextStatic.root().with(ANIMAL, "cat")
        assertNull(ContextStatic.current().get(ANIMAL))
        withContext(Dispatchers.Default + context1.asContextElement()) {
            assertEquals("cat", ContextStatic.current().get(ANIMAL))
        }
    }

    @Test
    fun noMakeCurrentNestedContextSucceeds() = runTest {
        val context1 = ContextStatic.root().with(ANIMAL, "cat")
        assertNull(ContextStatic.current().get(ANIMAL))
        withContext(Dispatchers.Default + context1.asContextElement()) {
            assertEquals("cat", ContextStatic.current().get(ANIMAL))
            withContext(ContextStatic.current().with(ANIMAL, "dog").asContextElement()) {
                assertEquals("dog", ContextStatic.current().get(ANIMAL))
            }
        }
    }

    @Test
    fun makeCurrentInNormalFunctionSucceeds() {
        assertNull(ContextStatic.current().get(ANIMAL))
        nonSuspendingContextFunction("dog")
    }

    @Test
    @Ignore //Why should doing this cause an exception?
    fun makeCurrentInTopLevelCoroutineFails() = runTest {
        val context1 = ContextStatic.root().with(ANIMAL, "cat")
        assertNull(ContextStatic.current().get(ANIMAL))
        assertFailsWith<AssertionError> {
            withContext(Dispatchers.Default + context1.asContextElement()) {
                assertEquals("cat", ContextStatic.current().get(ANIMAL))
                ContextStatic.current().with(ANIMAL, "dog").runWithActive {
                    assertEquals("dog", ContextStatic.current().get(ANIMAL))
                }
            }
        }
    }

    @Test
    fun makeCurrentInNestedCoroutineFails() = runTest {
        val context1 = ContextStatic.root().with(ANIMAL, "cat")
        assertNull(ContextStatic.current().get(ANIMAL))
        assertFailsWith<AssertionError> {
            withContext(Dispatchers.Default + context1.asContextElement()) {
                assertEquals("cat", ContextStatic.current().get(ANIMAL))
                withContext(Dispatchers.Default) {
                    assertEquals("cat", ContextStatic.current().get(ANIMAL))
                    ContextStatic.current().with(ANIMAL, "dog").runWithActive {
                        assertEquals("dog", ContextStatic.current().get(ANIMAL))
                    }
                }
            }
        }
    }

    @Test
    fun noMakeCurrentInSuspendingFunctionSucceeds() = runTest {
        val context1 = ContextStatic.root().with(ANIMAL, "cat")
        assertNull(ContextStatic.current().get(ANIMAL))
        withContext(Dispatchers.Default + context1.asContextElement()) {
            assertEquals("cat", ContextStatic.current().get(ANIMAL))
            suspendingFunctionContextElement("dog")
        }
    }

    fun nonSuspendingContextFunction(animal: String) {
        ContextStatic.current().with(ANIMAL, animal).runWithActive {
            assertEquals(animal, ContextStatic.current().get(ANIMAL))
        }
    }

    suspend fun suspendingFunctionContextElement(animal: String) {
        withContext(ContextStatic.current().with(ANIMAL, animal).asContextElement()) {
            assertEquals(animal, ContextStatic.current().get(ANIMAL))
            delay(10)
            assertEquals(animal, ContextStatic.current().get(ANIMAL))
        }
    }
}
