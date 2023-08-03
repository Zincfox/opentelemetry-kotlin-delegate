package io.opentelemetry.kotlindelegate.api.common

expect enum class AttributeTypeWrapper {
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
