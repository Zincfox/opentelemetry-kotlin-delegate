package io.opentelemetry.kotlindelegate.test

interface SpanMatchingStrategy {

    fun <T> filter(matches: List<T>): List<T>

    data object FirstMatching : SpanMatchingStrategy {

        override fun <T> filter(matches: List<T>): List<T> {
            return matches.firstOrNull()?.let(::listOf).orEmpty()
        }
    }
    data object LastMatching : SpanMatchingStrategy {

        override fun <T> filter(matches: List<T>): List<T> {
            return matches.lastOrNull()?.let(::listOf).orEmpty()
        }
    }
    data object AllMatching : SpanMatchingStrategy {

        override fun <T> filter(matches: List<T>): List<T> {
            return matches
        }
    }
    data class IndexInMatching(val index: Int) : SpanMatchingStrategy {
        override fun <T> filter(matches: List<T>): List<T> {
            return matches.elementAtOrNull(index)?.let(::listOf).orEmpty()
        }
    }
}
