package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.kotlindelegate.utils.java.BiConsumer

expect interface Attributes {

    fun forEach(consumer: BiConsumer<in AttributeKey<*>, in Any>)
    fun size(): Int
    fun isEmpty(): Boolean
    fun asMap(): Map<AttributeKey<*>, Any>
    fun toBuilder(): AttributesBuilder
}

expect object AttributesStatic {

    fun empty(): Attributes
    fun <T: Any> of(key: AttributeKey<T>, value: T): Attributes
    fun <T: Any, U: Any> of(key1: AttributeKey<T>, value1: T, key2: AttributeKey<U>, value2: U): Attributes
    fun <T: Any, U: Any, V: Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
    ): Attributes

    fun <T: Any, U: Any, V: Any, W: Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
        key4: AttributeKey<W>,
        value4: W,
    ): Attributes

    fun <T: Any, U: Any, V: Any, W: Any, X: Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
        key4: AttributeKey<W>,
        value4: W,
        key5: AttributeKey<X>,
        value5: X,
    ): Attributes

    fun <T: Any, U: Any, V: Any, W: Any, X: Any, Y: Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
        key4: AttributeKey<W>,
        value4: W,
        key5: AttributeKey<X>,
        value5: X,
        key6: AttributeKey<Y>,
        value6: Y,
    ): Attributes

    fun builder(): AttributesBuilder
}
