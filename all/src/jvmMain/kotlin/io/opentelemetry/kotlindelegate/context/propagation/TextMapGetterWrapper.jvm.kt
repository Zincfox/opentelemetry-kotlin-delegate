package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.context.propagation.TextMapGetter
import io.opentelemetry.kotlindelegate.utils.IWrapper

actual class TextMapGetterWrapper<C: Any>(
    override val wrappedObject: TextMapGetter<C>
) : WrapperBase<TextMapGetter<C>>(), IWrapper {

    actual fun keys(carrier: C): Iterable<String> = wrappedObject.keys(carrier)

    actual fun get(carrier: C?, key: String): String? = wrappedObject.get(carrier, key)
}
