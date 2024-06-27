package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.kotlindelegate.utils.java.Predicate

actual interface AttributesBuilder{

    actual fun build(): Attributes
    actual fun <T> put(
        key: AttributeKey<Long>,
        value: Int,
    ): AttributesBuilder

    actual fun <T> put(
        key: AttributeKey<T>,
        value: T,
    ): AttributesBuilder

    actual fun put(
        key: String,
        value: String,
    ): AttributesBuilder = put(AttributeKeyStatic.stringKey(key), value)

    actual fun put(
        key: String,
        value: Long,
    ): AttributesBuilder = put(AttributeKeyStatic.longKey(key), value)

    actual fun put(
        key: String,
        value: Double,
    ): AttributesBuilder = put(AttributeKeyStatic.doubleKey(key), value)

    actual fun put(
        key: String,
        value: Boolean,
    ): AttributesBuilder = put(AttributeKeyStatic.booleanKey(key), value)

    actual fun put(
        key: String,
        vararg value: String,
    ): AttributesBuilder = put(AttributeKeyStatic.stringArrayKey(key), value.toList())

    actual fun <T> put(
        key: AttributeKey<List<T>>,
        vararg value: T,
    ): AttributesBuilder  = put(key, value.toList())

    actual fun put(
        key: String,
        vararg value: Long,
    ): AttributesBuilder = put(AttributeKeyStatic.longArrayKey(key), value.toList())

    actual fun put(
        key: String,
        vararg value: Double,
    ): AttributesBuilder = put(AttributeKeyStatic.doubleArrayKey(key), value.toList())

    actual fun put(
        key: String,
        vararg value: Boolean,
    ): AttributesBuilder = put(AttributeKeyStatic.booleanArrayKey(key), value.toList())

    actual fun putAll(attributes: Attributes): AttributesBuilder
    actual fun <T> remove(key: AttributeKey<T>): AttributesBuilder = this
    actual fun removeIf(predicate: Predicate<AttributeKey<*>>): AttributesBuilder = this
}

internal class AttributesBuilderImpl() : AttributesBuilder {
    private val map: MutableMap<AttributeKey<*>, Any> = mutableMapOf()

    constructor(map: Map<AttributeKey<*>, Any>) : this() {
        this.map.putAll(map)
    }

    override fun build(): Attributes = AttributesImpl(map.toMap())

    override fun <T> put(key: AttributeKey<Long>, value: Int): AttributesBuilder = apply {
        map[key] = value
    }

    override fun <T> put(key: AttributeKey<T>, value: T): AttributesBuilder = apply {
        map[key] = value!!
    }

    override fun putAll(attributes: Attributes): AttributesBuilder = apply {
        map.putAll(attributes.asMap())
    }
}
