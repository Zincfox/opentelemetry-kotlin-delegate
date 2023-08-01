package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class AttributesBuilderWrapper : IWrapper {

    fun build(): AttributesWrapper

    fun <T:Any> put(key: AttributeKeyWrapper<T>, value: T): AttributesBuilderWrapper
    fun put(key: String, value: String): AttributesBuilderWrapper
    fun put(key: String, value: Long): AttributesBuilderWrapper
    fun put(key: String, value: Double): AttributesBuilderWrapper
    fun put(key: String, value: Boolean): AttributesBuilderWrapper

    fun <T:Any> put(key: AttributeKeyWrapper<List<T>>, vararg value: T): AttributesBuilderWrapper
    fun put(key: String, vararg value: String): AttributesBuilderWrapper
    fun put(key: String, vararg value: Long): AttributesBuilderWrapper
    fun put(key: String, vararg value: Double): AttributesBuilderWrapper
    fun put(key: String, vararg value: Boolean): AttributesBuilderWrapper

    fun putAll(attributes: AttributesWrapper): AttributesBuilderWrapper

    fun remove(key: AttributeKeyWrapper<*>): AttributesBuilderWrapper
    fun removeIf(filter: (AttributeKeyWrapper<*>) -> Boolean): AttributesBuilderWrapper
}
