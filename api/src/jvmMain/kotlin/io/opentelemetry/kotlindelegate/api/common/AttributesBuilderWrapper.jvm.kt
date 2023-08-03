package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.api.common.AttributesBuilder
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable


actual class AttributesBuilderWrapper(
    override val wrappedObject: AttributesBuilder,
) : WrapperBase<AttributesBuilder>(), IWrapper {

    actual fun build(): AttributesWrapper {
        return wrappedObject.build().letWrapImmutable(::AttributesWrapper)
    }

    actual fun <T : Any> put(key: AttributeKeyWrapper<T>, value: T): AttributesBuilderWrapper {
        wrappedObject.put(key.wrappedObject, value)
        return this
    }

    actual fun put(key: String, value: String): AttributesBuilderWrapper {
        wrappedObject.put(key, value)
        return this
    }

    actual fun put(key: String, value: Long): AttributesBuilderWrapper {
        wrappedObject.put(key, value)
        return this
    }

    actual fun put(key: String, value: Double): AttributesBuilderWrapper {
        wrappedObject.put(key, value)
        return this
    }

    actual fun put(key: String, value: Boolean): AttributesBuilderWrapper {
        wrappedObject.put(key, value)
        return this
    }

    actual fun <T : Any> put(key: AttributeKeyWrapper<List<T>>, vararg value: T): AttributesBuilderWrapper {
        wrappedObject.put(key.wrappedObject, *value)
        return this
    }

    actual fun put(key: String, vararg value: String): AttributesBuilderWrapper {
        wrappedObject.put(key, *value)
        return this
    }

    actual fun put(key: String, vararg value: Long): AttributesBuilderWrapper {
        wrappedObject.put(key, *value)
        return this
    }

    actual fun put(key: String, vararg value: Double): AttributesBuilderWrapper {
        wrappedObject.put(key, *value)
        return this
    }

    actual fun put(key: String, vararg value: Boolean): AttributesBuilderWrapper {
        wrappedObject.put(key, *value)
        return this
    }

    actual fun putAll(attributes: AttributesWrapper): AttributesBuilderWrapper {
        wrappedObject.putAll(attributes.wrappedObject)
        return this
    }

    actual fun remove(key: AttributeKeyWrapper<*>): AttributesBuilderWrapper {
        wrappedObject.remove(key.wrappedObject)
        return this
    }

    actual fun removeIf(filter: (AttributeKeyWrapper<*>) -> Boolean): AttributesBuilderWrapper {
        wrappedObject.removeIf { filter(AttributeKeyWrapper(it)) }
        return this
    }
}
