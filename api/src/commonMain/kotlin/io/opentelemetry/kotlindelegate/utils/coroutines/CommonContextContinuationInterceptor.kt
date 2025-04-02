package io.opentelemetry.kotlindelegate.utils.coroutines

import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.ContextStatic
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyed
import io.opentelemetry.kotlindelegate.context.runWithActive
import kotlin.coroutines.*

class CommonContextContinuationInterceptor(val context: Context) : ContinuationInterceptor {

    companion object {

        val Key = ContinuationInterceptor.Key
    }

    override fun toString(): String {
        return "CommonContextContinuationInterceptor[context=$context]"
    }

    override val key: CoroutineContext.Key<*>
        get() = Key

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return Continuation(continuation.context) { result ->
            context.runWithActive {
                continuation.resumeWith(result)
            }
        }
    }
}

fun Context.asContextElement(): ContinuationInterceptor {
    return CommonContextContinuationInterceptor(this)
}

fun ImplicitContextKeyed.asContextElement(): ContinuationInterceptor {
    return storeInContext(ContextStatic.current()).asContextElement()
}
