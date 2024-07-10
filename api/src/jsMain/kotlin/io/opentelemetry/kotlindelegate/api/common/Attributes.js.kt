package io.opentelemetry.kotlindelegate.api.common

import io.opentelemetry.kotlindelegate.js.*
import io.opentelemetry.kotlindelegate.utils.java.BiConsumer
import io.opentelemetry.kotlindelegate.js.Attributes as JSAttributes

actual interface Attributes {

    actual fun forEach(consumer: BiConsumer<in AttributeKey<*>, in Any>)
    actual fun size(): Int
    actual fun isEmpty(): Boolean
    actual fun asMap(): Map<AttributeKey<*>, Any>
    actual fun toBuilder(): AttributesBuilder
}

internal class AttributesImpl
private constructor(val map: Map<AttributeKey<*>, Any>, internal val jsAttributes: JSAttributes) : Attributes {

    companion object {

        private val arrayTypes = setOf(
            AttributeType.LONG_ARRAY,
            AttributeType.DOUBLE_ARRAY,
            AttributeType.STRING_ARRAY,
            AttributeType.BOOLEAN_ARRAY,
        )

        internal fun toJSAttributes(map: Map<AttributeKey<*>, Any>): JSAttributes {
            val attributes: JSAttributes = js("{}").unsafeCast<JSAttributes>()
            for ((key, value) in map) {
                if (key.getType() in arrayTypes)
                    attributes[key.getKey()] = (value as List<*>).toTypedArray()
                else
                    attributes[key.getKey()] = value
            }
            return attributes
        }
    }

    internal constructor(map: Map<AttributeKey<*>, Any> = emptyMap()) : this(map, toJSAttributes(map))
    internal constructor(attributes: JSAttributes) : this(attributes.toMap().mapNotNull { (key, value) ->
        when (value) {
            null -> return@mapNotNull null
            is String -> AttributeKeyStatic.stringKey(key)

            is Boolean -> AttributeKeyStatic.booleanKey(key)

            is Long, is ULong,
            is Int, is UInt,
            is Short, is UShort,
            is Byte, is UByte,
            -> AttributeKeyStatic.longKey(key)

            is Number -> AttributeKeyStatic.doubleKey(key)

            is Array<*> -> {
                if (value.isEmpty()) return@mapNotNull null
                when (value[0]) {
                    is String -> AttributeKeyStatic.stringArrayKey(key)
                    is Boolean -> AttributeKeyStatic.booleanArrayKey(key)
                    is Double, Float -> AttributeKeyStatic.doubleArrayKey(key)
                    else -> {
                        if (value.all {
                                it is Long || it is ULong ||
                                        it is Int || it is UInt ||
                                        it is Short || it is UShort ||
                                        it is Byte || it is UByte
                            })
                            AttributeKeyStatic.longArrayKey(key)
                        else
                            AttributeKeyStatic.doubleArrayKey(key)
                    }
                }
            }
            else -> return@mapNotNull null
        } to value
    }.toMap(), attributes)

    override fun forEach(consumer: BiConsumer<in AttributeKey<*>, in Any>) =
        map.forEach { consumer.accept(it.key, it.value) }

    override fun asMap(): Map<AttributeKey<*>, Any> = map

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun size(): Int = map.size
    override fun toBuilder(): AttributesBuilder = AttributesBuilderImpl(map)
}

fun Attributes.asJsAttributes(): JSAttributes = when (this) {
    is AttributesImpl -> jsAttributes
    else -> AttributesImpl.toJSAttributes(asMap())
}

fun JSAttributes.asCommonAttributes(): Attributes = AttributesImpl(this)

actual object AttributesStatic {

    private val emptyAttributes: Attributes = AttributesImpl()

    actual fun empty(): Attributes = emptyAttributes

    actual fun <T : Any> of(
        key: AttributeKey<T>,
        value: T,
    ): Attributes = AttributesImpl(mapOf(key to value))

    actual fun <T : Any, U : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
    ): Attributes = AttributesImpl(mapOf(key1 to value1, key2 to value2))

    actual fun <T : Any, U : Any, V : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
    ): Attributes = AttributesImpl(mapOf(key1 to value1, key2 to value2, key3 to value3))

    actual fun <T : Any, U : Any, V : Any, W : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
        key4: AttributeKey<W>,
        value4: W,
    ): Attributes = AttributesImpl(mapOf(key1 to value1, key2 to value2, key3 to value3, key4 to value4))

    actual fun <T : Any, U : Any, V : Any, W : Any, X : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
        key4: AttributeKey<W>,
        value4: W,
        key5: AttributeKey<X>,
        value5: X,
    ): Attributes =
        AttributesImpl(mapOf(key1 to value1, key2 to value2, key3 to value3, key4 to value4, key5 to value5))

    actual fun <T : Any, U : Any, V : Any, W : Any, X : Any, Y : Any> of(
        key1: AttributeKey<T>,
        value1: T,
        key2: AttributeKey<U>,
        value2: U,
        key3: AttributeKey<V>,
        value3: V,
        key4: AttributeKey<W>,
        value4: W,
        key5: AttributeKey<X>,
        value5: X,
        key6: AttributeKey<Y>,
        value6: Y,
    ): Attributes = AttributesImpl(
        mapOf(
            key1 to value1,
            key2 to value2,
            key3 to value3,
            key4 to value4,
            key5 to value5,
            key6 to value6
        )
    )

    actual fun builder(): AttributesBuilder = AttributesBuilderImpl()
}
