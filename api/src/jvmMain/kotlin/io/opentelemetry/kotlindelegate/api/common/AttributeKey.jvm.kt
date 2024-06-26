package io.opentelemetry.kotlindelegate.api.common

actual typealias AttributeKey<T> = io.opentelemetry.api.common.AttributeKey<T>

actual object AttributeKeyStatic {

    actual fun stringKey(key: String): AttributeKey<String> = AttributeKey.stringKey(key)

    actual fun booleanKey(key: String): AttributeKey<Boolean> = AttributeKey.booleanKey(key)

    actual fun longKey(key: String): AttributeKey<Long> = AttributeKey.longKey(key)

    actual fun doubleKey(key: String): AttributeKey<Double> = AttributeKey.doubleKey(key)

    actual fun stringArrayKey(key: String): AttributeKey<List<String>> = AttributeKey.stringArrayKey(key)

    actual fun booleanArrayKey(key: String): AttributeKey<List<Boolean>> = AttributeKey.booleanArrayKey(key)

    actual fun longArrayKey(key: String): AttributeKey<List<Long>> = AttributeKey.longArrayKey(key)

    actual fun doubleArrayKey(key: String): AttributeKey<List<Double>> = AttributeKey.doubleArrayKey(key)
}
