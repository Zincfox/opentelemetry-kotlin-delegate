package io.opentelemetry.kotlindelegate.context

expect interface ImplicitContextKeyed {
    fun storeInContext(context: Context): Context
}

expect inline fun <R> ImplicitContextKeyed.runWithActive(crossinline block: () -> R): R
expect suspend inline fun <R> ImplicitContextKeyed.runWithActiveSuspend(crossinline block: suspend () -> R): R
