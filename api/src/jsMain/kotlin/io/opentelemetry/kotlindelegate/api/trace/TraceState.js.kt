package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.createTraceState
import io.opentelemetry.kotlindelegate.js.TraceState as JsTraceState

import io.opentelemetry.kotlindelegate.utils.java.BiConsumer
import io.opentelemetry.kotlindelegate.utils.java.Consumer

actual interface TraceState {

    actual fun get(key: String): String?
    actual fun size(): Int
    actual fun isEmpty(): Boolean
    actual fun forEach(consumer: BiConsumer<String, String>)
    actual fun asMap(): Map<String, String>
    actual fun toBuilder(): TraceStateBuilder
}

internal class TraceStateCommonImpl(val jsTraceState: JsTraceState) : TraceState {

    private var parsedCache: List<Pair<String, String>>? = null
    val parsed: List<Pair<String, String>>
        get() {
            if (parsedCache == null) {
                parsedCache = parse(jsTraceState)
            }
            return parsedCache!!
        }

    companion object {

        private val whitespaceRegex = Regex("\\s+")

        fun parse(jsTraceState: JsTraceState): List<Pair<String, String>> {
            return jsTraceState.serialize().replace(whitespaceRegex, "").split(',').map {
                val (key, value) = it.split('=', limit = 2)
                key to value
            }
        }
    }

    override fun get(key: String): String? {
        return jsTraceState.get(key)
    }

    override fun size(): Int {
        return parsed.size
    }

    override fun isEmpty(): Boolean {
        return parsed.isEmpty()
    }

    override fun forEach(consumer: BiConsumer<String, String>) {
        parsed.forEach { consumer.accept(it.first, it.second) }
    }

    override fun asMap(): Map<String, String> {
        return parsed.toMap()
    }

    override fun toBuilder(): TraceStateBuilder {
        return TraceStateBuilderImpl(jsTraceState)
    }
}

fun TraceState.asJsTraceState(): JsTraceState = when (this) {
    is TraceStateCommonImpl -> jsTraceState
    else -> {
        val tempList = mutableListOf<Pair<String, String>>()
        // store values in tempList to enable foldRight -> oldest (last) key-value pair added first, followed by newer
        forEach { key, value ->
            tempList.add(key to value)
        }
        tempList.foldRight(createTraceState()) { nxt, acc ->
            acc.set(nxt.first, nxt.second)
        }
    }
}

actual object TraceStateStatic {

    internal val defaultJsTraceState: JsTraceState = createTraceState()
    private val defaultTraceState = TraceStateCommonImpl(defaultJsTraceState)

    init {
        defaultTraceState.parsed //calculate initial parsing as sideeffect of getting property
    }

    actual fun getDefault(): TraceState {
        return defaultTraceState
    }

    actual fun builder(): TraceStateBuilder {
        return TraceStateBuilderImpl()
    }
}
