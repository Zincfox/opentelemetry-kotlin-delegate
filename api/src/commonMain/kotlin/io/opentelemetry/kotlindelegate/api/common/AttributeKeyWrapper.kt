package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class AttributeKeyWrapper<T:Any> : IWrapper {

    val key: String
    val type: AttributeTypeWrapper

    companion object {

        fun stringKey(key: String): AttributeKeyWrapper<String>
        fun booleanKey(key: String): AttributeKeyWrapper<Boolean>
        fun longKey(key: String): AttributeKeyWrapper<Long>
        fun doubleKey(key: String): AttributeKeyWrapper<Double>
        fun stringArrayKey(key: String): AttributeKeyWrapper<List<String>>
        fun booleanArrayKey(key: String): AttributeKeyWrapper<List<Boolean>>
        fun longArrayKey(key: String): AttributeKeyWrapper<List<Long>>
        fun doubleArrayKey(key: String): AttributeKeyWrapper<List<Double>>
    }
}
