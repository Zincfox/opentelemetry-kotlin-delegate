package io.opentelemetry.kotlindelegate.api.common

expect class AttributesWrapper : Map<AttributeKeyWrapper<*>, Any> {

    operator fun <T:Any> get(key: AttributeKeyWrapper<T>): T?

    companion object {

        val empty: AttributesWrapper
        fun <T:Any> of(
            key: AttributeKeyWrapper<T>, value: T,
        ): AttributesWrapper

        fun <T:Any, U:Any> of(
            key1: AttributeKeyWrapper<T>, value1: T,
            key2: AttributeKeyWrapper<U>, value2: U,
        ): AttributesWrapper

        fun <T:Any, U:Any, V:Any> of(
            key1: AttributeKeyWrapper<T>, value1: T,
            key2: AttributeKeyWrapper<U>, value2: U,
            key3: AttributeKeyWrapper<V>, value3: V,
        ): AttributesWrapper

        fun <T:Any, U:Any, V:Any, W:Any> of(
            key1: AttributeKeyWrapper<T>, value1: T,
            key2: AttributeKeyWrapper<U>, value2: U,
            key3: AttributeKeyWrapper<V>, value3: V,
            key4: AttributeKeyWrapper<W>, value4: W,
        ): AttributesWrapper

        fun <T:Any, U:Any, V:Any, W:Any, X:Any> of(
            key1: AttributeKeyWrapper<T>, value1: T,
            key2: AttributeKeyWrapper<U>, value2: U,
            key3: AttributeKeyWrapper<V>, value3: V,
            key4: AttributeKeyWrapper<W>, value4: W,
            key5: AttributeKeyWrapper<X>, value5: X,
        ): AttributesWrapper

        fun <T:Any, U:Any, V:Any, W:Any, X:Any, Y:Any> of(
            key1: AttributeKeyWrapper<T>, value1: T,
            key2: AttributeKeyWrapper<U>, value2: U,
            key3: AttributeKeyWrapper<V>, value3: V,
            key4: AttributeKeyWrapper<W>, value4: W,
            key5: AttributeKeyWrapper<X>, value5: X,
            key6: AttributeKeyWrapper<Y>, value6: Y,
        ): AttributesWrapper

        fun builder(): AttributesBuilderWrapper
    }

    fun toBuilder(): AttributesBuilderWrapper
}

fun AttributesWrapper.Companion.build(block: AttributesBuilderWrapper.() -> Unit): AttributesWrapper {
    return builder().apply(block).build()
}
