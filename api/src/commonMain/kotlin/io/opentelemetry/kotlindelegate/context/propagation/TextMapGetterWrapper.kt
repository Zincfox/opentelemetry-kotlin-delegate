package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class TextMapGetterWrapper<C: Any> : IWrapper {

    fun keys(carrier: C): Iterable<String>
    fun get(carrier: C?, key: String): String?
}
