package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.api.common.Attributes
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class AttributesWrapper(
    override val wrappedObject: Attributes,
) : WrapperBase<Attributes>(), IWrapper, Map<AttributeKeyWrapper<*>, Any> {

    private val map: Map<AttributeKeyWrapper<*>, Any> by lazy {
        wrappedObject.asMap().mapKeys { AttributeKeyWrapper(it.key) }
    }

    @JvmName("getTyped")
    actual operator fun <T : Any> get(key: AttributeKeyWrapper<T>): T? {
        return wrappedObject.get(key.wrappedObject)
    }

    actual companion object {

        actual val empty: AttributesWrapper by lazy {
            Attributes.empty().letWrapImmutable(::AttributesWrapper)
        }

        actual fun <T : Any> of(
            key: AttributeKeyWrapper<T>,
            value: T,
        ): AttributesWrapper {
            return Attributes.of(
                key.wrappedObject,
                value,
            ).letWrapImmutable(::AttributesWrapper)
        }

        actual fun <T : Any, U : Any> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
        ): AttributesWrapper {
            return Attributes.of(
                key1.wrappedObject,
                value1,
                key2.wrappedObject,
                value2,
            ).letWrapImmutable(::AttributesWrapper)
        }

        actual fun <T : Any, U : Any, V : Any> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
            key3: AttributeKeyWrapper<V>,
            value3: V,
        ): AttributesWrapper {
            return Attributes.of(
                key1.wrappedObject,
                value1,
                key2.wrappedObject,
                value2,
                key3.wrappedObject,
                value3,
            ).letWrapImmutable(::AttributesWrapper)
        }

        actual fun <T : Any, U : Any, V : Any, W : Any> of(
            key1: AttributeKeyWrapper<T>,
            value1: T,
            key2: AttributeKeyWrapper<U>,
            value2: U,
            key3: AttributeKeyWrapper<V>,
            value3: V,
            key4: AttributeKeyWrapper<W>,
            value4: W,
        ): AttributesWrapper {
            return Attributes.of(
                key1.wrappedObject,
                value1,
                key2.wrappedObject,
                value2,
                key3.wrappedObject,
                value3,
                key4.wrappedObject,
                value4,
            ).letWrapImmutable(::AttributesWrapper)
        }

        actual fun <T : Any, U : Any, V : Any, W : Any, X : Any> of(
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
        ): AttributesWrapper {
            return Attributes.of(
                key1.wrappedObject,
                value1,
                key2.wrappedObject,
                value2,
                key3.wrappedObject,
                value3,
                key4.wrappedObject,
                value4,
                key5.wrappedObject,
                value5,
            ).letWrapImmutable(::AttributesWrapper)
        }

        actual fun <T : Any, U : Any, V : Any, W : Any, X : Any, Y : Any> of(
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
        ): AttributesWrapper {
            return Attributes.of(
                key1.wrappedObject,
                value1,
                key2.wrappedObject,
                value2,
                key3.wrappedObject,
                value3,
                key4.wrappedObject,
                value4,
                key6.wrappedObject,
                value6,
            ).letWrapImmutable(::AttributesWrapper)
        }

        actual fun builder(): AttributesBuilderWrapper {
            return Attributes.builder().let(::AttributesBuilderWrapper)
        }
    }

    actual fun toBuilder(): AttributesBuilderWrapper {
        return wrappedObject.toBuilder().let(::AttributesBuilderWrapper)
    }

    override val entries: Set<Map.Entry<AttributeKeyWrapper<*>, Any>> by lazy {
        map.entries
    }
    override val keys: Set<AttributeKeyWrapper<*>> by lazy {
        map.keys
    }
    override val size: Int by lazy {
        map.size
    }
    override val values: Collection<Any> by lazy {
        map.values
    }

    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    override fun get(key: AttributeKeyWrapper<*>): Any? {
        return map[key]
    }

    override fun containsValue(value: Any): Boolean {
        return map.containsValue(value)
    }

    override fun containsKey(key: AttributeKeyWrapper<*>): Boolean {
        return map.containsKey(key)
    }
}
