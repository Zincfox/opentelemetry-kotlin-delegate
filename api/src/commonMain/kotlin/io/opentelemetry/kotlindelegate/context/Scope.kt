package io.opentelemetry.kotlindelegate.context

expect interface Scope : io.opentelemetry.kotlindelegate.utils.java.AutoCloseable {
    override fun close()
}

expect object ScopeStatic {
    fun noop(): Scope
}
