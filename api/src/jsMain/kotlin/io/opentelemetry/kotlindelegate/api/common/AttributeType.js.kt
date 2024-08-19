package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.kotlindelegate.js.AttributeValue

actual enum class AttributeType {
    STRING,
    BOOLEAN,
    LONG,
    DOUBLE,
    STRING_ARRAY,
    BOOLEAN_ARRAY,
    LONG_ARRAY,
    DOUBLE_ARRAY,
    ;
}

@Suppress("UNCHECKED_CAST")
fun AttributeType.forceJsType(value: Any): AttributeValue = when (this) {
    AttributeType.STRING -> value as String
    AttributeType.BOOLEAN -> value as Boolean
    AttributeType.LONG -> (value as Long).toDouble()
    AttributeType.DOUBLE -> value as Double
    AttributeType.STRING_ARRAY -> (value as List<String>).toTypedArray()
    AttributeType.BOOLEAN_ARRAY -> (value as List<Boolean>).toTypedArray()
    AttributeType.LONG_ARRAY -> (value as List<Long>).map { it.toDouble() }.toTypedArray()
    AttributeType.DOUBLE_ARRAY -> (value as List<Double>).toTypedArray()
}

@Suppress("UNCHECKED_CAST", "USELESS_CAST")
fun AttributeType.forceCommonType(value: AttributeValue): Any = when (this) {
    AttributeType.STRING -> value as String
    AttributeType.BOOLEAN -> value as Boolean
    AttributeType.LONG -> when(value) {
        is Long -> value as Long
        is Double -> (value as Double).toLong()
        else -> (value as Number).toLong()
    }
    AttributeType.DOUBLE -> value as Double
    AttributeType.STRING_ARRAY -> (value as Array<String>).toList()
    AttributeType.BOOLEAN_ARRAY -> (value as Array<Boolean>).toList()
    AttributeType.LONG_ARRAY -> (value as Array<Double>).map { it.toLong() }
    AttributeType.DOUBLE_ARRAY -> (value as Array<Double>).toList()
}
