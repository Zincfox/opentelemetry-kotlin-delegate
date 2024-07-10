package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.js.createContextKey
import io.opentelemetry.kotlindelegate.js.ContextKey as JsContextKey

actual interface ContextKey<V>
actual object ContextKeyStatic {

    actual fun <T> named(name: String): ContextKey<T> {
        return ContextKeyCommonAdapter(createContextKey(name))
    }
}

interface IContextKeyAdapter<T> : ContextKey<T> {

    val innerContextKey: JsContextKey
}

internal val <T> ContextKey<T>.jsContextKey: JsContextKey
    get() = (this as? IContextKeyAdapter<*>
        ?: throw IllegalArgumentException("Cannot extract inner js-ContextKey from '${this}' (implement IContextKeyAdapter?)")).innerContextKey

internal class ContextKeyCommonAdapter<T>(override val innerContextKey: JsContextKey): IContextKeyAdapter<T> {
    override fun hashCode(): Int {
        return innerContextKey.hashCode()
    }

    override fun toString(): String {
        return "ContextKeyCommonAdapter(innerContextKey='$innerContextKey')"
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }
}

fun <T> JsContextKey.asCommonContextKey(): IContextKeyAdapter<T> = ContextKeyCommonAdapter(this)
fun IContextKeyAdapter<*>.asJsContextKey(): JsContextKey = innerContextKey
