package io.opentelemetry.kotlindelegate.api.common

expect interface AttributeKey<T> {
    fun getKey(): String
    fun getType(): AttributeType
}

expect object AttributeKeyStatic {
    fun stringKey(key: String): AttributeKey<String>
    fun booleanKey(key: String): AttributeKey<Boolean>
    fun longKey(key: String): AttributeKey<Long>
    fun doubleKey(key: String): AttributeKey<Double>
    fun stringArrayKey(key: String): AttributeKey<List<String>>
    fun booleanArrayKey(key: String): AttributeKey<List<Boolean>>
    fun longArrayKey(key: String): AttributeKey<List<Long>>
    fun doubleArrayKey(key: String): AttributeKey<List<Double>>
}
