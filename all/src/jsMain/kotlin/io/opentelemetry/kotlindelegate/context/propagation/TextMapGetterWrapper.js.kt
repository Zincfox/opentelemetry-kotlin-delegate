package io.opentelemetry.kotlindelegate.context.propagation

actual class TextMapGetterWrapper<C: Any> : IWrapper {

    actual fun keys(carrier: C): Iterable<String> {
        TODO("Not yet implemented")
    }

    actual fun get(carrier: C?, key: String): String? {
        TODO("Not yet implemented")
    }
}
