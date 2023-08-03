package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.api.common.AttributeType
import io.opentelemetry.kotlindelegate.utils.IWrapper

actual enum class AttributeTypeWrapper(override val wrappedObject: AttributeType) : IWrapper {
    STRING(AttributeType.STRING),
    BOOLEAN(AttributeType.BOOLEAN),
    LONG(AttributeType.LONG),
    DOUBLE(AttributeType.DOUBLE),
    STRING_ARRAY(AttributeType.STRING_ARRAY),
    BOOLEAN_ARRAY(AttributeType.BOOLEAN_ARRAY),
    LONG_ARRAY(AttributeType.LONG_ARRAY),
    DOUBLE_ARRAY(AttributeType.DOUBLE_ARRAY),
    ;

    companion object {

        private val lookupMap: Map<AttributeType, AttributeTypeWrapper> =
            AttributeTypeWrapper.entries.associateBy { it.wrappedObject }

        fun fromOriginal(original: AttributeType): AttributeTypeWrapper {
            return lookupMap.getValue(original)
        }
    }
}
