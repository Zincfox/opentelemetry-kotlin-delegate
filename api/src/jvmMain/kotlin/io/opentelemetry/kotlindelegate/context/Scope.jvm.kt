package io.opentelemetry.kotlindelegate.context

actual typealias Scope = io.opentelemetry.context.Scope

actual object ScopeStatic {

    actual fun noop(): Scope = Scope.noop()
}
