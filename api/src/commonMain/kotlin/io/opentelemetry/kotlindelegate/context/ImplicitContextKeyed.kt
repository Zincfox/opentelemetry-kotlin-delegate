package io.opentelemetry.kotlindelegate.context

expect interface ImplicitContextKeyed {
    open fun makeCurrent(): Scope
    fun storeInContext(context: Context): Context
}
