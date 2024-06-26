package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.kotlindelegate.utils.java.Predicate

expect interface AttributesBuilder {
    fun build(): Attributes
    // <T> should not be required for `Long` but was added accidentally in jvm version of original opentelemetry library
    // and is now part of the interface until the next major version
    fun <T> put(key: AttributeKey<Long>, value: Int): AttributesBuilder
    fun <T> put(key: AttributeKey<T>, value: T): AttributesBuilder
    open fun put(key: String, value: String): AttributesBuilder
    open fun put(key: String, value: Long): AttributesBuilder
    open fun put(key: String, value: Double): AttributesBuilder
    open fun put(key: String, value: Boolean): AttributesBuilder
    open fun put(key: String, vararg value: String): AttributesBuilder
    open fun <T> put(key: AttributeKey<List<T>>, vararg value: T): AttributesBuilder
    open fun put(key: String, vararg value: Long): AttributesBuilder
    open fun put(key: String, vararg value: Double): AttributesBuilder
    open fun put(key: String, vararg value: Boolean): AttributesBuilder
    fun putAll(attributes: Attributes): AttributesBuilder
    open fun <T> remove(key: AttributeKey<T>): AttributesBuilder
    open fun removeIf(predicate: Predicate<AttributeKey<*>>): AttributesBuilder
}
