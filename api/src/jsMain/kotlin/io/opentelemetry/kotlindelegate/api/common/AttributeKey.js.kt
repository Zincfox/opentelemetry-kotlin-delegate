package io.opentelemetry.kotlindelegate.api.common

actual interface AttributeKey<T> {

    actual fun getKey(): String
    actual fun getType(): AttributeType
}

internal class AttributeKeyImpl<T>(val key: String, val type: AttributeType) : AttributeKey<T>, Comparable<AttributeKey<*>> {

    override fun getKey(): String = key
    override fun getType(): AttributeType = type

    override fun toString(): String {
        return "AttributeKeyImpl(key='$key', type=${type.name})"
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other === this || (other != null && other is AttributeKey<*> && other.getKey() == key)
    }

    override fun compareTo(other: AttributeKey<*>): Int {
        return key.compareTo(other.getKey())
    }
}

actual object AttributeKeyStatic {

    actual fun stringKey(key: String): AttributeKey<String> = AttributeKeyImpl(key, AttributeType.STRING)

    actual fun booleanKey(key: String): AttributeKey<Boolean> = AttributeKeyImpl(key, AttributeType.BOOLEAN)

    actual fun longKey(key: String): AttributeKey<Long> = AttributeKeyImpl(key, AttributeType.LONG)

    actual fun doubleKey(key: String): AttributeKey<Double> = AttributeKeyImpl(key, AttributeType.DOUBLE)

    actual fun stringArrayKey(key: String): AttributeKey<List<String>> =
        AttributeKeyImpl(key, AttributeType.STRING_ARRAY)

    actual fun booleanArrayKey(key: String): AttributeKey<List<Boolean>> =
        AttributeKeyImpl(key, AttributeType.BOOLEAN_ARRAY)

    actual fun longArrayKey(key: String): AttributeKey<List<Long>> = AttributeKeyImpl(key, AttributeType.LONG_ARRAY)

    actual fun doubleArrayKey(key: String): AttributeKey<List<Double>> =
        AttributeKeyImpl(key, AttributeType.DOUBLE_ARRAY)
}
