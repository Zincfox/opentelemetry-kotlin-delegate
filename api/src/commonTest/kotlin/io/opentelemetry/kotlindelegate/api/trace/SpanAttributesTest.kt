package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.api.GlobalOpenTelemetry
import io.opentelemetry.kotlindelegate.api.common.AttributeKeyStatic
import io.opentelemetry.kotlindelegate.api.common.AttributesBuilder
import io.opentelemetry.kotlindelegate.api.common.AttributesStatic
import io.opentelemetry.kotlindelegate.test.OpenTelemetryTesting
import io.opentelemetry.kotlindelegate.test.assert
import io.opentelemetry.kotlindelegate.utils.startSpan
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SpanAttributesTest {

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
    fun defaultAttributesTest() {
        val tracer = GlobalOpenTelemetry.getTracer("defaultAttributesTest")
        val span = tracer.startSpan("span")
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        assertEquals(AttributesStatic.empty(), data.attributes, "Default attribute-list is not empty")
                    }
                }
            }
        }
    }

    fun attributeTestTemplate(
        scopeName: String,
        builderBlock: SpanBuilder.() -> Unit,
        spanBlock: Span.() -> Unit,
        expectedBlock: AttributesBuilder.() -> Unit,
    ) {
        val tracer = GlobalOpenTelemetry.getTracer(scopeName)
        val span = tracer.startSpan("span", builderBlock)
        span.spanBlock()
        span.end()
        recorder.assert {
            singleTrace {
                ordered {
                    "span" {
                        val expected = AttributesStatic.builder().apply(expectedBlock).build()
                        assertAttributesEquals(expected)
                        val actual = data.attributes
                        assertEquals(expected.asMap(), actual.asMap())
                    }
                }
            }
        }
    }

    @Test
    fun initialAttributesAllTest() {
        val initialAttributes = AttributesStatic.of(AttributeKeyStatic.stringKey("initial1"), "value1", AttributeKeyStatic.longKey("initial2"), 2)
        attributeTestTemplate("initialAttributesAllTest",
                              builderBlock = {
                                  setAllAttributes(initialAttributes)
                              },
                              spanBlock = {},
                              expectedBlock = {
                                  putAll(initialAttributes)
                              })
    }
    @Test
    fun initialAttributesSetTest() {
        attributeTestTemplate("initialAttributesSetTest",
                              builderBlock = {
                                  setAttribute("initial1", "value1")
                                  setAttribute("initial2", 2L)
                              },
                              spanBlock = {},
                              expectedBlock = {
                                  put("initial1", "value1")
                                  put("initial2", 2L)
                              })
    }
    @Test
    fun runningAttributesAllTest() {
        val runningAttributes = AttributesStatic.of(AttributeKeyStatic.stringKey("running1"), "v1", AttributeKeyStatic.longKey("running2"), 20)
        attributeTestTemplate("runningAttributesAllTest",
                              builderBlock = {},
                              spanBlock = {
                                  setAllAttributes(runningAttributes)
                              },
                              expectedBlock = {
                                  putAll(runningAttributes)
                              })
    }
    @Test
    fun runningAttributesSetTest() {
        attributeTestTemplate("runningAttributesSetTest",
                              builderBlock = {},
                              spanBlock = {
                                  setAttribute("running1", "v1")
                                  setAttribute("running2", 20L)
                              },
                              expectedBlock = {
                                  put("running1", "v1")
                                  put("running2", 20L)
                              })
    }
    @Test
    fun additionalAttributesAllTest() {
        val initialAttributes = AttributesStatic.of(AttributeKeyStatic.stringKey("initial1"), "value1", AttributeKeyStatic.longKey("initial2"), 2)
        val runningAttributes = AttributesStatic.of(AttributeKeyStatic.stringKey("running1"), "v1", AttributeKeyStatic.longKey("running2"), 20)
        attributeTestTemplate("additionalAttributesAllTest",
                              builderBlock = {
                                  setAllAttributes(initialAttributes)
                              },
                              spanBlock = {
                                  setAllAttributes(runningAttributes)
                              },
                              expectedBlock = {
                                  putAll(initialAttributes)
                                  putAll(runningAttributes)
                              })
    }
    @Test
    fun additionalAttributesSetTest() {
        attributeTestTemplate("additionalAttributesSetTest",
                              builderBlock = {
                                  setAttribute("initial1", "value1")
                                  setAttribute("initial2", 2L)
                              },
                              spanBlock = {
                                  setAttribute("running1", "v1")
                                  setAttribute("running2", 20L)
                              },
                              expectedBlock = {
                                  put("initial1", "value1")
                                  put("initial2", 2L)
                                  put("running1", "v1")
                                  put("running2", 20L)
                              })
    }
    @Test
    fun overwriteAttributesAllTest() {
        val initialAttributes = AttributesStatic.of(AttributeKeyStatic.stringKey("key1"), "value1", AttributeKeyStatic.longKey("initial2"), 2)
        val runningAttributes = AttributesStatic.of(AttributeKeyStatic.stringKey("key1"), "v1", AttributeKeyStatic.longKey("running2"), 20)
        attributeTestTemplate("overwriteAttributesAllTest",
                              builderBlock = {
                                  setAllAttributes(initialAttributes)
                              },
                              spanBlock = {
                                  setAllAttributes(runningAttributes)
                              },
                              expectedBlock = {
                                  putAll(
                                      AttributesStatic.of(
                                          AttributeKeyStatic.longKey("initial2"), 2,
                                          AttributeKeyStatic.stringKey("key1"), "v1",
                                          AttributeKeyStatic.longKey("running2"), 20,
                                      )
                                  )
                              })
    }
    @Test
    fun overwriteAttributesSetTest() {
        attributeTestTemplate("overwriteAttributesSetTest",
                              builderBlock = {
                                  setAttribute("key1", "value1")
                                  setAttribute("initial2", 2L)
                              },
                              spanBlock = {
                                  setAttribute("key1", "v1")
                                  setAttribute("running2", 20L)
                              },
                              expectedBlock = {
                                  put("initial2", 2L)
                                  put("key1", "v1")
                                  put("running2", 20L)
                              })
    }
}
