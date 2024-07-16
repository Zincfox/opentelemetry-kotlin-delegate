package io.opentelemetry.kotlindelegate.test

import io.opentelemetry.kotlindelegate.api.trace.StatusCode

open class TraceForestPrinter(val recorder: TraceForestRecorder) {

    open fun printDebugForest(indent: Int = 0, indentString: String = "  ") {
        println(writeDebugForest(indent, indentString).joinToString(separator = "\n"))
    }

    open fun writeDebugForest(indent: Int = 0, indentString: String = "  "): List<String> =
        buildList {
            add(indentString.repeat(indent) + recorder.toString())
            recorder.traces.forEach {
                addAll(writeDebugTree(it, indent + 1, indentString))
            }
        }

    open fun printDebugTree(traceId: String, indent: Int = 0, indentString: String = "  ") {
        println(writeDebugTree(traceId, indent, indentString).joinToString(separator = "\n"))
    }

    open fun writeDebugTree(traceId: String, indent: Int = 0, indentString: String = "  "): List<String> = buildList {
        add(indentString.repeat(indent) + "Trace: $traceId")
        for (root in recorder.getTraceRootSpans(traceId)) {
            addAll(writeDebugSpan(root, indent + 1, indentString))
        }
    }

    protected open fun writeDebugSpan(span: SpanData, indent: Int, indentString: String): List<String> = buildList {
        val spanString = buildString {
            if (span.name.isNotEmpty()) {
                this@buildString.append(span.name)
                this@buildString.append(" (")
                this@buildString.append(span.spanId)
                this@buildString.append(')')
            } else {
                this@buildString.append(span.spanId)
            }
            var nextSeparatorString = ": "
            if(span.statusCode != StatusCode.UNSET){
                this@buildString.append(nextSeparatorString)
                this@buildString.append(span.statusCode.name)
                nextSeparatorString = ", "
            }
            if(!span.attributes.isEmpty()) {
                this@buildString.append(nextSeparatorString)
                nextSeparatorString = ", "
                this@buildString.append('[')
                var firstKey = true
                span.attributes.forEach {key, value ->
                    if(!firstKey) {
                        append(", ")
                    }
                    firstKey = false
                    append(key.getKey(), " = ", value.toString())
                }
                this@buildString.append(']')
            }
        }
        add(indentString.repeat(indent) + spanString)
        for (child in recorder.getSpanChildren(span)) {
            addAll(writeDebugSpan(child, indent + 1, indentString))
        }
    }
}
