package io.opentelemetry.kotlindelegate.api.common

actual class AttributesWrapper : Map<AttributeKeyWrapper<*>, Any> {

    actual operator fun <T> get(key: AttributeKeyWrapper<T>): T? {
        TODO("Not yet implemented")
    }

    actual companion object {

        actual fun empty(): AttributesWrapper {
            TODO("Not yet implemented")
        }

        actual fun <T> of(
            key: AttributeKeyWrapper<T>,
            value: T,
        ): AttributesWrapper {
            TODO("Not yet implemented")
        }

        actual fun <T, U> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
        ): AttributesWrapper {
            TODO("Not yet implemented")
        }

        actual fun <T, U, V> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
            key3: AttributeKeyWrapper<V>,
            value3: V,
        ) {
        }

        actual fun <T, U, V, W> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
            key3: AttributeKeyWrapper<V>,
            value3: V,
            key4: AttributeKeyWrapper<W>,
            value4: W,
        ) {
        }

        actual fun <T, U, V, W, X> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
            key3: AttributeKeyWrapper<V>,
            value3: V,
            key4: AttributeKeyWrapper<W>,
            value4: W,
            key5: AttributeKeyWrapper<X>,
            value5: X,
        ) {
        }

        actual fun <T, U, V, W, X, Y> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
            key3: AttributeKeyWrapper<V>,
            value3: V,
            key4: AttributeKeyWrapper<W>,
            value4: W,
            key5: AttributeKeyWrapper<X>,
            value5: X,
            key6: AttributeKeyWrapper<Y>,
            value6: Y,
        ) {
        }

        actual fun builder(): AttributesBuilderWrapper {
            TODO("Not yet implemented")
        }
    }

    actual fun toBuilder(): AttributesBuilderWrapper {
        TODO("Not yet implemented")
    }
}
