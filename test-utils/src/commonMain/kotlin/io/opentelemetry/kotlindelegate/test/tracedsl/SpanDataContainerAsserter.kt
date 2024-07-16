package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.test.SpanMatchingStrategy
import kotlin.test.*

@OtelTraceDslMarker
interface SpanDataContainerAsserter<out ChildAsserter : SpanDataAsserter<ChildAsserter>> {

    val path: String

    val recursiveChildren: Sequence<ChildAsserter>
        get() = directChildren.flatMap { sequenceOf(it) + it.recursiveChildren }
    val directChildren: Sequence<ChildAsserter>

    fun firstRecursiveChild(block: ChildAsserter.() -> Unit) {
        val rChildren = recursiveChildren.toList()
        assertNotEquals(emptyList(), rChildren, "No children found at $path")
        rChildren.first().block()
    }

    fun lastRecursiveChild(block: ChildAsserter.() -> Unit) {
        val rChildren = recursiveChildren.toList()
        assertNotEquals(emptyList(), rChildren, "No children found at $path")
        rChildren.last().block()
    }

    fun singleRecursiveChild(block: ChildAsserter.() -> Unit) {
        val rChildren = recursiveChildren.toList()
        assertNotEquals(emptyList(), rChildren, "No children found at $path")
        assertEquals(1, rChildren.size, "Found multiple children at $path: ${rChildren.map { it.data.name }}")
        rChildren.single().block()
    }

    fun singleRecursiveChild(name: String, block: ChildAsserter.() -> Unit) = singleRecursiveChild {
        assertName(name)
        block()
    }

    fun singleRecursiveChild(name: Regex, block: ChildAsserter.() -> Unit) = singleRecursiveChild {
        assertName(name)
        block()
    }

    fun allRecursiveChildren(block: ChildAsserter.() -> Unit) {
        recursiveChildren.forEach(block)
    }

    fun firstDirectChild(block: ChildAsserter.() -> Unit) {
        val dChildren = directChildren.toList()
        assertNotEquals(emptyList(), dChildren, "No direct children found at $path")
        dChildren.first().block()
    }

    fun firstDirectChild(name: String, block: ChildAsserter.() -> Unit) = firstDirectChild {
        assertName(name)
        block()
    }

    fun firstDirectChild(name: Regex, block: ChildAsserter.() -> Unit) = firstDirectChild {
        assertName(name)
        block()
    }

    fun lastDirectChild(block: ChildAsserter.() -> Unit) {
        val dChildren = directChildren.toList()
        assertNotEquals(emptyList(), dChildren, "No direct children found at $path")
        dChildren.last().block()
    }

    fun lastDirectChild(name: String, block: ChildAsserter.() -> Unit) = lastDirectChild {
        assertName(name)
        block()
    }

    fun lastDirectChild(name: Regex, block: ChildAsserter.() -> Unit) = lastDirectChild {
        assertName(name)
        block()
    }

    fun singleDirectChild(block: ChildAsserter.() -> Unit) {
        val dChildren = directChildren.toList()
        assertNotEquals(emptyList(), dChildren, "No direct children found at $path")
        assertEquals(
            1,
            dChildren.size,
            "Found more than one single direct child at $path: ${dChildren.map { it.data.name }}"
        )
        dChildren.single().block()
    }

    fun singleDirectChild(name: String, block: ChildAsserter.() -> Unit) = singleDirectChild {
        assertName(name)
        block()
    }

    fun singleDirectChild(name: Regex, block: ChildAsserter.() -> Unit) = singleDirectChild {
        assertName(name)
        block()
    }

    fun allDirectChildren(block: ChildAsserter.() -> Unit) {
        directChildren.forEach(block)
    }

    fun assertNoChildren() {
        val dChildren = directChildren.toList()
        assertEquals(0, dChildren.size, "Found direct children at $path: ${dChildren.map { it.data.name }}")
    }

    operator fun Int.invoke(required: Boolean = true, block: ChildAsserter.() -> Unit) {
        val dChildren = directChildren.toList()
        val index = if (this < 0) dChildren.size - this else this
        if (required) {
            assertNotEquals(0, dChildren.size, "No direct children found at $path")
            assertContains(
                dChildren.indices,
                index,
                "Direct child with index $this ($index) not found at $path: ${dChildren.map { it.data.name }}"
            )
            dChildren[index].block()
        }
        dChildren.elementAtOrNull(index)?.block()
    }

    fun ordered(
        open: Boolean = false,
        block: OrderedNameAsserter<ChildAsserter, SpanDataContainerAsserter<ChildAsserter>>.() -> Unit,
    ) {
        val asserter = DefaultOrderedNameAsserter(this, open).also(block)
        if (!open) asserter.assertNoMoreChildren()
    }

    fun unordered(block: UnorderedNameAsserter<ChildAsserter, SpanDataContainerAsserter<ChildAsserter>>.() -> Unit) {
        DefaultUnorderedNameAsserter(this).block()
    }

    interface OrderedNameAsserter<
            out ChildAsserter : SpanDataAsserter<ChildAsserter>,
            out BaseAsserter : SpanDataContainerAsserter<ChildAsserter>,
            > :
            SpanDataContainerAsserter<OrderedNameAsserter.OrderedNameSpanAsserter<ChildAsserter>> {

        val currentChildSpanIndex: Int

        fun <T> baseAsserter(block: BaseAsserter.() -> T): T

        fun assertNoMoreChildren() {
            assertEquals(
                currentChildSpanIndex,
                this.directChildren.count(),
                "Unexpected further children at $path"
            )
        }

        operator fun String.invoke(
            open: Boolean? = null,
            block: OrderedNameSpanAsserter<ChildAsserter>.() -> Unit,
        )

        operator fun Regex.invoke(
            open: Boolean? = null,
            block: OrderedNameSpanAsserter<ChildAsserter>.(match: MatchResult) -> Unit,
        )

        interface OrderedNameSpanAsserter<out ChildAsserter : SpanDataAsserter<ChildAsserter>> :
                OrderedNameAsserter<ChildAsserter, ChildAsserter>,
                SpanDataAsserter<OrderedNameSpanAsserter<ChildAsserter>>
    }

    interface UnorderedNameAsserter<
            out ChildAsserter : SpanDataAsserter<ChildAsserter>,
            out BaseAsserter : SpanDataContainerAsserter<ChildAsserter>,
            > :
            SpanDataContainerAsserter<UnorderedNameAsserter.UnorderedNameSpanAsserter<ChildAsserter>> {

        fun <T> baseAsserter(block: BaseAsserter.() -> T): T

        operator fun String.invoke(
            required: Boolean = true,
            matchingStrategy: SpanMatchingStrategy = SpanMatchingStrategy.AllMatching,
            block: UnorderedNameSpanAsserter<ChildAsserter>.() -> Unit,
        )

        operator fun Regex.invoke(
            required: Boolean = true,
            matchingStrategy: SpanMatchingStrategy = SpanMatchingStrategy.AllMatching,
            block: UnorderedNameSpanAsserter<ChildAsserter>.(match: MatchResult) -> Unit,
        )

        interface UnorderedNameSpanAsserter<out ChildAsserter : SpanDataAsserter<ChildAsserter>> :
                UnorderedNameAsserter<ChildAsserter, ChildAsserter>,
                SpanDataAsserter<UnorderedNameSpanAsserter<ChildAsserter>>
    }
}
