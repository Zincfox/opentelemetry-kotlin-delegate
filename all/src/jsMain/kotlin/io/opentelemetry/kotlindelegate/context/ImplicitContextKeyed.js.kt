package io.opentelemetry.kotlindelegate.context

actual open class ImplicitContextKeyedWrapper : IWrapper {

    actual open fun makeCurrent() {
    }

    actual open fun storeInContext(context: ContextWrapper): ContextWrapper {
        TODO("Not yet implemented")
    }
}
