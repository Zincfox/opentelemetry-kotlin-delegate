@file:OptIn(ExperimentalContracts::class)

package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.extension.kotlin.asContextElement as asContextElementJVM
import io.opentelemetry.kotlindelegate.utils.coroutines.asContextElement as asContextElementKotlin
import kotlinx.coroutines.withContext
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

actual typealias ImplicitContextKeyed = io.opentelemetry.context.ImplicitContextKeyed

actual inline fun <R> ImplicitContextKeyed.runWithActive(crossinline block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return makeCurrent().use { block() }
}

actual suspend inline fun <R> ImplicitContextKeyed.runWithActiveSuspend(
    crossinline block: suspend () -> R,
): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return withContext(this.asContextElementKotlin()) { block() }
}
