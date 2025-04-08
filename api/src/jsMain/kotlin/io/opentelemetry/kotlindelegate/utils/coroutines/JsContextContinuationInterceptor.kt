package io.opentelemetry.kotlindelegate.utils.coroutines

import kotlin.coroutines.*
import io.opentelemetry.kotlindelegate.js.Context as JsContext
import io.opentelemetry.kotlindelegate.js.context as JsContextAPI

class JsContextContinuationInterceptor(val context: JsContext) : ContinuationInterceptor {

    companion object {

        val Key = ContinuationInterceptor.Key
    }

    override fun toString(): String {
        return "JsContextContinuationInterceptor[context=$context]"
    }

    override val key: CoroutineContext.Key<*>
        get() = Key

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return Continuation(continuation.context) { result ->
            JsContextAPI.with(context, Continuation<T>::resumeWith, undefined, continuation, result)
        }
    }
}
