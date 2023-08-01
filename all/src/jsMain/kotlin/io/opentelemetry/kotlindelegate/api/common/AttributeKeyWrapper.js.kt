package io.opentelemetry.kotlindelegate.api.common

actual class AttributeKeyWrapper<T:Any> {

    actual val key: String
        get() = TODO("Not yet implemented")
    actual val type: AttributeTypeWrapper
        get() = TODO("Not yet implemented")

    actual companion object {

        actual fun stringKey(key: String): AttributeKeyWrapper<String> {
            TODO("Not yet implemented")
        }

        actual fun booleanKey(key: String): AttributeKeyWrapper<Boolean> {
            TODO("Not yet implemented")
        }

        actual fun longKey(key: String): AttributeKeyWrapper<Long> {
            TODO("Not yet implemented")
        }

        actual fun doubleKey(key: String): AttributeKeyWrapper<Double> {
            TODO("Not yet implemented")
        }

        actual fun stringArrayKey(key: String): AttributeKeyWrapper<List<String>> {
            TODO("Not yet implemented")
        }

        actual fun booleanArrayKey(key: String): AttributeKeyWrapper<List<Boolean>> {
            TODO("Not yet implemented")
        }

        actual fun longArrayKey(key: String): AttributeKeyWrapper<List<Long>> {
            TODO("Not yet implemented")
        }

        actual fun doubleArrayKey(key: String): AttributeKeyWrapper<List<Double>> {
            TODO("Not yet implemented")
        }
    }
}
