package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.js.JsRecord
import io.opentelemetry.kotlindelegate.js.set
import io.opentelemetry.kotlindelegate.js.TextMapSetter as JsTextMapSetter

actual fun interface TextMapSetter<C> {

    actual fun set(carrier: C?, key: String, value: String)
}

private class TextMapSetterJsAdapter<C : Any>(val setter: TextMapSetter<C>) : JsTextMapSetter<C> {

    override fun set(carrier: C, key: String, value: String) {
        setter.set(carrier, key, value)
    }
}

private class TextMapSetterCommonAdapter<C : Any>(val setter: JsTextMapSetter<C>) : TextMapSetter<C> {

    override fun set(carrier: C?, key: String, value: String) {
        setter.set(carrier ?: return, key, value)
    }
}

private class NullUnitSetterAdapter(val setter: TextMapSetter<*>): JsTextMapSetter<Unit> {

    override fun set(carrier: Unit, key: String, value: String) {
        setter.set(null, key, value)
    }
}

internal fun <C : Any> TextMapSetter<C>.asJsSetter(): JsTextMapSetter<C> = when (this) {
    is TextMapSetterCommonAdapter<C> -> this.setter
    is TextMapRecordSetter -> JsTextMapRecordSetter
    else -> TextMapSetterJsAdapter(this)
}

internal fun <C> TextMapSetter<C>.asNullUnitJsSetter(): JsTextMapSetter<Unit> = NullUnitSetterAdapter(this)

internal fun <C : Any> JsTextMapSetter<C>.asCommonSetter(): TextMapSetter<C> = when (this) {
    is TextMapSetterJsAdapter<C> -> this.setter
    is JsTextMapRecordSetter -> TextMapRecordSetter.unsafeCast<TextMapSetter<C>>()
    else -> TextMapSetterCommonAdapter(this)
}

internal object TextMapRecordSetter : TextMapSetter<Any> {

    fun <T : Any> forType(): TextMapSetter<T> = this.unsafeCast<TextMapSetter<T>>()

    override fun set(carrier: Any?, key: String, value: String) {
        (carrier.unsafeCast<JsRecord<String, String>>())[key] = value
    }
}


internal object JsTextMapRecordSetter : JsTextMapSetter<Any> {

    fun <T : Any> forType(): TextMapSetter<T> = this.unsafeCast<TextMapSetter<T>>()

    override fun set(carrier: Any, key: String, value: String) {
        (carrier.unsafeCast<JsRecord<String, String>>())[key] = value
    }
}
