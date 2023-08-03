package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class ContextKeyWrapper<T> : IWrapper {
    companion object {

        fun <T> named(name: String): ContextKeyWrapper<T>
    }
}
