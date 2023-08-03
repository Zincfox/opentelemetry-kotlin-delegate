package io.opentelemetry.kotlindelegate.utils

import kotlin.reflect.KProperty

class CachedWriteOnlyAdapter<T>(
    initial: T,
    private val setter: (T)->Unit
) {
    private var value: T = initial
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        setter(value)
    }
}
