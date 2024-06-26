package io.opentelemetry.kotlindelegate.context

expect interface Scope : AutoCloseable {
    override fun close()
}

expect object ScopeStatic {
    fun noop(): Scope
}
