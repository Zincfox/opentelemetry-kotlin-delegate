package io.opentelemetry.kotlindelegate.context.propagation

expect interface TextMapGetter<C> {
    fun keys(carrier: C): Iterable<String>
    fun get(carrier: C?, key: String): String?
}
