package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.js.JsRecord
import io.opentelemetry.kotlindelegate.js.get
import io.opentelemetry.kotlindelegate.js.keys
import io.opentelemetry.kotlindelegate.js.TextMapGetter as JsTextMapGetter

actual interface TextMapGetter<C> {

    actual fun keys(carrier: C): Iterable<String>
    actual fun get(carrier: C?, key: String): String?
}

private class NullUnitGetterAdapter(val getter: TextMapGetter<*>) : JsTextMapGetter<Unit> {

    override fun keys(carrier: Unit): Array<String> {
        return emptyArray()
    }

    override fun get(carrier: Unit, key: String): Any? {
        return getter.get(null, key)
    }
}

internal fun <C:Any> TextMapGetter<C>.asJsGetter(): JsTextMapGetter<C> = when(this) {
    is TextMapGetterCommonAdapter -> this.getter
    TextMapRecordGetter -> JsTextMapRecordGetter
    else -> TextMapGetterJsAdapter(this)
}

internal fun <C : Any> TextMapGetter<C>.asNullUnitJsGetter(): JsTextMapGetter<Unit> = NullUnitGetterAdapter(this)

internal fun <C : Any> JsTextMapGetter<C>.asCommonGetter(): TextMapGetter<C> = when (this) {
    is TextMapGetterJsAdapter<C> -> this.getter
    JsTextMapRecordGetter -> TextMapRecordGetter.unsafeCast<TextMapGetter<C>>()
    else -> TextMapGetterCommonAdapter(this)
}

private class TextMapGetterJsAdapter<C : Any>(val getter: TextMapGetter<C>) : JsTextMapGetter<C> {

    override fun keys(carrier: C): Array<String> {
        return getter.keys(carrier).toList().toTypedArray()
    }

    override fun get(carrier: C, key: String): Any? {
        return getter.get(carrier, key)
    }
}

private class TextMapGetterCommonAdapter<C : Any>(val getter: JsTextMapGetter<C>) : TextMapGetter<C> {

    override fun keys(carrier: C): Iterable<String> = getter.keys(carrier).toList()
    override fun get(carrier: C?, key: String): String? {
        return getter.get(carrier ?: return null, key).let {
            when (it) {
                is String -> it
                is Array<*> -> it.first()?.toString()
                undefined -> null
                null -> null
                else -> it.toString()
            }
        }
    }
}

internal object TextMapRecordGetter : TextMapGetter<Any> {

    fun <T : Any> forType(): TextMapGetter<T> = this.unsafeCast<TextMapGetter<T>>()
    override fun keys(carrier: Any): Iterable<String> {
        return (carrier.unsafeCast<JsRecord<String, *>>()).keys().toList()
    }

    override fun get(carrier: Any?, key: String): String? {
        if (carrier == null || carrier == undefined) return null
        return (carrier.unsafeCast<JsRecord<String, String>>())[key]
    }
}

internal object JsTextMapRecordGetter : JsTextMapGetter<Any> {

    fun <T : Any> forType(): JsTextMapGetter<T> = this.unsafeCast<JsTextMapGetter<T>>()
    override fun keys(carrier: Any): Array<String> {
        return (carrier.unsafeCast<JsRecord<String, *>>()).keys()
    }

    override fun get(carrier: Any, key: String): Any? {
        return (carrier.unsafeCast<JsRecord<String, *>>())[key]
    }
}
