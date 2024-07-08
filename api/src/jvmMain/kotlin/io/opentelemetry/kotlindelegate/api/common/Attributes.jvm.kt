package io.opentelemetry.kotlindelegate.api.common

actual typealias Attributes = io.opentelemetry.api.common.Attributes

actual object AttributesStatic {

    actual fun empty(): Attributes = Attributes.empty()

    actual fun <T : Any> of(
        key: AttributeKey<T>,
        value: T,
    ): Attributes = Attributes.of(key, value)

    actual fun <T : Any, U : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
    ): Attributes = Attributes.of(key1, value1, key2, value2)

    actual fun <T : Any, U : Any, V : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
    ): Attributes = Attributes.of(key1, value1, key2, value2, key3, value3)

    actual fun <T : Any, U : Any, V : Any, W : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
        key4: AttributeKey<W>,
        value4: W,
    ): Attributes = Attributes.of(key1, value1, key2, value2, key3, value3, key4, value4)

    actual fun <T : Any, U : Any, V : Any, W : Any, X : Any> of(
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
    ): Attributes = Attributes.of(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5)

    actual fun <T : Any, U : Any, V : Any, W : Any, X : Any, Y : Any> of(
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
    ): Attributes = Attributes.of(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5, key6, value6)

    actual fun builder(): AttributesBuilder = Attributes.builder()
}
