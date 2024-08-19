@file:OptIn(ExperimentalContracts::class)

package io.opentelemetry.kotlindelegate.context

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

actual interface ImplicitContextKeyed {

    actual fun storeInContext(context: Context): Context
}

actual inline fun <R> ImplicitContextKeyed.runWithActive(crossinline block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return storeInContext(ContextStatic.current()).runWithActive(block)
}

actual suspend inline fun <R> ImplicitContextKeyed.runWithActiveSuspend(
    crossinline block: suspend () -> R,
): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return storeInContext(ContextStatic.current()).runWithActiveSuspend(block)
}
