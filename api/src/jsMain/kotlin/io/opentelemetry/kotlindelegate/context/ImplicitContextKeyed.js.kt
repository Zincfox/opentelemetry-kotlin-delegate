package io.opentelemetry.kotlindelegate.context

actual interface ImplicitContextKeyed {

    actual fun storeInContext(context: Context): Context
}

actual inline fun <R> ImplicitContextKeyed.runWithActive(crossinline block: () -> R): R {
    return storeInContext(ContextStatic.current()).runWithActive(block)
}
