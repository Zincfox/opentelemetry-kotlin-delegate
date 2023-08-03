package io.opentelemetry.kotlindelegate.api.common


actual class AttributesBuilderWrapper : IWrapper {

    actual fun build(): AttributesWrapper {
        TODO()
    }

    actual fun <T> put(key: AttributeKeyWrapper<T>, value: T): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, value: String): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, value: Long): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, value: Double): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, value: Boolean): AttributesBuilderWrapper {
        TODO()
    }

    actual fun <T> put(key: AttributeKeyWrapper<List<T>>, vararg value: T): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, vararg value: String): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, vararg value: Long): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, vararg value: Double): AttributesBuilderWrapper {
        TODO()
    }

    actual fun put(key: String, vararg value: Boolean): AttributesBuilderWrapper {
        TODO()
    }

    actual fun putAll(attributes: AttributesWrapper): AttributesBuilderWrapper {
        TODO()
    }

    actual fun remove(key: AttributeKeyWrapper<*>): AttributesBuilderWrapper {
        TODO()
    }

    actual fun removeIf(filter: (AttributeKeyWrapper<*>) -> Boolean): AttributesBuilderWrapper {
        TODO()
    }
}
