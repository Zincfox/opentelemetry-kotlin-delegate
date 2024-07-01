package io.opentelemetry.kotlindelegate.context

expect interface ImplicitContextKeyed {
    fun storeInContext(context: Context): Context
}

expect inline fun <R> ImplicitContextKeyed.runWithActive(crossinline block: () -> R): R
