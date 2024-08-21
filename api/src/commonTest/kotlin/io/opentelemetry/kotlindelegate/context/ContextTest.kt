package io.opentelemetry.kotlindelegate.context

import kotlinx.coroutines.test.runTest
import kotlin.test.*

class ContextTest {

    data class SimpleContextElement(val value: String) : ImplicitContextKeyed {

        companion object {

            val Key: ContextKey<SimpleContextElement> = ContextKeyStatic.named("SimpleContextElement")
        }

        override fun storeInContext(context: Context): Context {
            return context.with(Key, this)
        }
    }

    data class DifferentContextElement(val value: String) : ImplicitContextKeyed {

        companion object {

            val Key: ContextKey<DifferentContextElement> = ContextKeyStatic.named("DifferentContextElement")
        }

        override fun storeInContext(context: Context): Context {
            return context.with(Key, this)
        }
    }

    @Test
    fun simpleModelTest() {
        val rootContext = ContextStatic.root()
        assertNull(
            rootContext.get(SimpleContextElement.Key),
            "SimpleContextElement should not be present in root initially"
        )
        val contextElement = SimpleContextElement("111")
        val subContext1 = rootContext.with(contextElement)
        assertNull(
            rootContext.get(SimpleContextElement.Key),
            "SimpleContextElement should not be present in root after storing in subcontext"
        )
        val storedContextElement = subContext1.get(SimpleContextElement.Key)
        assertEquals(
            contextElement,
            storedContextElement,
            "Stored SimpleContextElement should match the one used for the subcontext"
        )
    }

    @Test
    fun transitivityTest() {
        val rootContext = ContextStatic.root()
        assertNull(
            rootContext.get(SimpleContextElement.Key),
            "SimpleContextElement should not be present in root initially"
        )
        val contextElement1 = SimpleContextElement("111")
        val subContext1 = rootContext.with(contextElement1)
        assertNull(
            rootContext.get(SimpleContextElement.Key),
            "SimpleContextElement should not be present in root after storing in subcontext"
        )
        val storedContextElement = subContext1.get(SimpleContextElement.Key)
        assertEquals(
            contextElement1,
            storedContextElement,
            "Stored SimpleContextElement should match the one used for the subcontext"
        )
        val differentContextElement1 = DifferentContextElement("333")
        val subContext2 = subContext1.with(differentContextElement1)
        assertEquals(
            differentContextElement1,
            subContext2.get(DifferentContextElement.Key),
            "Stored DifferentContextElement should match the one used for the its subcontext"
        )
        assertEquals(
            contextElement1,
            storedContextElement,
            "Stored SimpleContextElement should still match the one used for the initial subcontext in child contexts"
        )
    }

    @Test
    fun overrideTest() {
        val rootContext = ContextStatic.root()
        val contextElement1 = SimpleContextElement("111")
        val contextElement2 = SimpleContextElement("222")
        val subContext1 = rootContext.with(contextElement1)
        val subContext2 = subContext1.with(contextElement2)
        assertNull(
            rootContext.get(SimpleContextElement.Key),
            "SimpleContextElement should not be present in root context"
        )
        assertEquals(
            contextElement1,
            subContext1.get(SimpleContextElement.Key),
            "SimpleContextElement should match contextElement1 in subContext1"
        )
        assertEquals(
            contextElement2,
            subContext2.get(SimpleContextElement.Key),
            "SimpleContextElement should match contextElement2 in subContext2"
        )
    }

    @Test
    fun changeCurrentTest() {
        val rootContext = ContextStatic.root()
        val contextElement1 = SimpleContextElement("111")
        val differentContextElement1 = DifferentContextElement("333")
        assertEquals(rootContext, ContextStatic.current(), "Initial context should be root context")
        assertNull(rootContext.get(SimpleContextElement.Key), "SimpleContextKey should not be present in root context")
        assertNull(rootContext.get(DifferentContextElement.Key), "DifferentContextKey should not be present in root context")
        contextElement1.runWithActive {
            assertEquals(
                contextElement1,
                ContextStatic.current().get(SimpleContextElement.Key),
                "SimpleContextElement should match contextElement1 in context with contextElement1"
            )
            assertNull(ContextStatic.current().get(DifferentContextElement.Key), "DifferentContextKey should not be present in contextElement1-context")
            differentContextElement1.runWithActive {
                assertEquals(
                    differentContextElement1,
                    ContextStatic.current().get(DifferentContextElement.Key),
                    "Stored DifferentContextElement should match the one used for the its subcontext"
                )
                assertEquals(
                    contextElement1,
                    ContextStatic.current().get(SimpleContextElement.Key),
                    "Stored SimpleContextElement should still match the one used for the initial subcontext in differentContextElement1.runWithActive"
                )
            }
            assertEquals(
                contextElement1,
                ContextStatic.current().get(SimpleContextElement.Key),
                "SimpleContextElement should match contextElement1 in context with contextElement1 after differentContextElement1.runWithActive"
            )
            assertNull(ContextStatic.current().get(DifferentContextElement.Key), "DifferentContextKey should not be present in contextElement1-context after its runWithActive")
        }
        assertNull(rootContext.get(SimpleContextElement.Key), "SimpleContextKey should not be present in root context after runWithActive")
        assertNull(rootContext.get(DifferentContextElement.Key), "DifferentContextKey should not be present in root context after runWithActive")
    }
    @Test
    fun changeCurrentSuspendTest() = runTest {
        val rootContext = ContextStatic.root()
        val contextElement1 = SimpleContextElement("111")
        val differentContextElement1 = DifferentContextElement("333")
        val contextElement2 = SimpleContextElement("222")
        assertEquals(rootContext, ContextStatic.current(), "Initial context should be root context")
        assertNull(rootContext.get(SimpleContextElement.Key), "SimpleContextKey should not be present in root context")
        assertNull(rootContext.get(DifferentContextElement.Key), "DifferentContextKey should not be present in root context")
        contextElement1.runWithActiveSuspend {
            assertEquals(
                contextElement1,
                ContextStatic.current().get(SimpleContextElement.Key),
                "SimpleContextElement should match contextElement1 in context with contextElement1"
            )
            assertNull(ContextStatic.current().get(DifferentContextElement.Key), "DifferentContextKey should not be present in contextElement1-context")
            differentContextElement1.runWithActiveSuspend {
                assertEquals(
                    differentContextElement1,
                    ContextStatic.current().get(DifferentContextElement.Key),
                    "Stored DifferentContextElement should match the one used for the its subcontext"
                )
                assertEquals(
                    contextElement1,
                    ContextStatic.current().get(SimpleContextElement.Key),
                    "Stored SimpleContextElement should still match the one used for the initial subcontext in differentContextElement1.runWithActive"
                )
                contextElement2.runWithActive {
                    assertEquals(
                        contextElement2,
                        ContextStatic.current().get(SimpleContextElement.Key),
                        "Stored SimpleContextElement should match contextElement2 in its own subcontext"
                    )
                    assertEquals(
                        differentContextElement1,
                        ContextStatic.current().get(DifferentContextElement.Key),
                        "Stored DifferentContextElement should match the one used for the its subcontext even inside contextElement2.runWithActive"
                    )
                }
                assertEquals(
                    differentContextElement1,
                    ContextStatic.current().get(DifferentContextElement.Key),
                    "Stored DifferentContextElement should match the one used for the its subcontext after contextElement2.runWithActive"
                )
                assertEquals(
                    contextElement1,
                    ContextStatic.current().get(SimpleContextElement.Key),
                    "Stored SimpleContextElement should still match the one used for its initial subcontext after contextElement2.runWithActive"
                )
            }
            assertEquals(
                contextElement1,
                ContextStatic.current().get(SimpleContextElement.Key),
                "SimpleContextElement should match contextElement1 in context with contextElement1 after differentContextElement1.runWithActive"
            )
            assertNull(ContextStatic.current().get(DifferentContextElement.Key), "DifferentContextKey should not be present in contextElement1-context after its runWithActive")
        }
        assertNull(rootContext.get(SimpleContextElement.Key), "SimpleContextKey should not be present in root context after runWithActive")
        assertNull(rootContext.get(DifferentContextElement.Key), "DifferentContextKey should not be present in root context after runWithActive")
    }
}
