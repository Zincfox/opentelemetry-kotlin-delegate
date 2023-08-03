package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect open class ImplicitContextKeyedWrapper : IWrapper {

    open fun makeCurrent()
    open fun storeInContext(context: ContextWrapper): ContextWrapper
}
