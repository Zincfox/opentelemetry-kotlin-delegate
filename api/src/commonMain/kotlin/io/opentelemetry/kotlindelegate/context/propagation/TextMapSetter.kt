package io.opentelemetry.kotlindelegate.context.propagation

expect interface TextMapSetter<C> {
    fun set(carrier: C?, key: String, value: String)
}
