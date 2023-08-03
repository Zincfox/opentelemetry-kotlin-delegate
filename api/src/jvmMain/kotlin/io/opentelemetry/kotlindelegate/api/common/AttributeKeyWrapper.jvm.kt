package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class AttributeKeyWrapper<T:Any>(
    override val wrappedObject: AttributeKey<T>
): WrapperBase<AttributeKey<T>>(), IWrapper {

    actual val key: String = wrappedObject.key
    actual val type: AttributeTypeWrapper by lazy {
        wrappedObject.type.let(AttributeTypeWrapper::fromOriginal)
    }

    actual companion object {

        actual fun stringKey(key: String): AttributeKeyWrapper<String> {
            return AttributeKey.stringKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }

        actual fun booleanKey(key: String): AttributeKeyWrapper<Boolean> {
            return AttributeKey.booleanKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }

        actual fun longKey(key: String): AttributeKeyWrapper<Long> {
            return AttributeKey.longKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }

        actual fun doubleKey(key: String): AttributeKeyWrapper<Double> {
            return AttributeKey.doubleKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }

        actual fun stringArrayKey(key: String): AttributeKeyWrapper<List<String>> {
            return AttributeKey.stringArrayKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }

        actual fun booleanArrayKey(key: String): AttributeKeyWrapper<List<Boolean>> {
            return AttributeKey.booleanArrayKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }

        actual fun longArrayKey(key: String): AttributeKeyWrapper<List<Long>> {
            return AttributeKey.longArrayKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }

        actual fun doubleArrayKey(key: String): AttributeKeyWrapper<List<Double>> {
            return AttributeKey.doubleArrayKey(key).letWrapImmutable(::AttributeKeyWrapper)
        }
    }
}
