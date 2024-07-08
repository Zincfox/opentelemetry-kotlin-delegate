package io.opentelemetry.kotlindelegate.context

actual interface ImplicitContextKeyed {

    actual fun storeInContext(context: Context): Context
}

actual inline fun <R> ImplicitContextKeyed.runWithActive(crossinline block: () -> R): R {
    return storeInContext(ContextStatic.current()).runWithActive(block)
}

actual suspend inline fun <R> ImplicitContextKeyed.runWithActiveSuspend(
    crossinline block: suspend () -> R,
): R {
    return storeInContext(ContextStatic.current()).runWithActiveSuspend(block)
}
