package io.opentelemetry.kotlindelegate.js

external interface JsRecord<K, T>

private fun <K, T> jsRecordSet(record: JsRecord<K, T>, key: K, value: T) {
    js("record[key]=value")
}

operator fun <K, T> JsRecord<K, T>.set(key: K, value: T) {
    jsRecordSet(this, key, value)
}

private fun <K, T> jsRecordGet(record: JsRecord<K, T>, key: K): T {
    return js("record[key]") as T
}

operator fun <K, T> JsRecord<K, T>.get(key: K): T = jsRecordGet(this, key)

private fun <K> jsRecordKeys(record: JsRecord<K, *>): Array<K> {
    return (js("Object.keys(record)") as Array<K>)
}

fun <K> JsRecord<K, *>.keys(): Array<K> = jsRecordKeys(this)

fun <K, T> JsRecord<K, T>.toMap(): Map<K, T> = keys().associateWith { this[it] }

fun <K, T, M : MutableMap<in K, in T>> JsRecord<K, T>.toMap(target: M) = target.also { m ->
    keys().associateWithTo(m) { this[it] }
}

fun <K, T> JsRecord<K, T>.toMutableMap(): MutableMap<K, T> = keys().associateWithTo(mutableMapOf()) { this[it] }

fun <K, T> emptyJsRecord(): JsRecord<K, T> = js("{}").unsafeCast<JsRecord<K, T>>()

fun <K, T> Map<out K, T>.toJsRecord(): JsRecord<K, T> = emptyJsRecord<K, T>().also { r ->
    forEach { (key, value) -> r[key] = value }
}
