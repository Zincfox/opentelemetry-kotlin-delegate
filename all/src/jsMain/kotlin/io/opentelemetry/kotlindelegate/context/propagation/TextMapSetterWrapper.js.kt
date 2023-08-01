package io.opentelemetry.kotlindelegate.context.propagation

actual class TextMapSetterWrapper<C> : IWrapper {

    actual fun set(carrier: C?, key: String, value: String) {
    }
}
