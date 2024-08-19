package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.test.SpanData

open class DefaultOrderedNameAsserter<
        out ChildAsserter : SpanDataAsserter<ChildAsserter>,
        out BaseAsserter : SpanDataContainerAsserter<ChildAsserter>,
        >(
    protected val sourceAsserter: BaseAsserter,
    protected val open: Boolean = false,
) : SpanDataContainerAsserter.OrderedNameAsserter<ChildAsserter, BaseAsserter> {

    override val path: String
        get() = sourceAsserter.path

    override fun <T> baseAsserter(block: BaseAsserter.() -> T): T = sourceAsserter.block()

    override val directChildren: Sequence<SpanDataContainerAsserter.OrderedNameAsserter.OrderedNameSpanAsserter<ChildAsserter>>
        get() = sourceAsserter.directChildren.map { DefaultOrderedNameSpanAsserter(it, open) }

    override var currentChildSpanIndex = 0
        protected set

    override fun String.invoke(
        open: Boolean?,
        block: SpanDataContainerAsserter.OrderedNameAsserter.OrderedNameSpanAsserter<ChildAsserter>.() -> Unit,
    ) {
        val effectiveOpen = open ?: this@DefaultOrderedNameAsserter.open
        with(sourceAsserter) {
            (this@DefaultOrderedNameAsserter.currentChildSpanIndex++) {
                assertName(this@invoke)
                val asserter = DefaultOrderedNameSpanAsserter(this, effectiveOpen).also(block)
                if (!effectiveOpen) {
                    asserter.assertNoMoreChildren()
                }
            }
        }
    }

    override operator fun Regex.invoke(
        open: Boolean?,
        block: SpanDataContainerAsserter.OrderedNameAsserter.OrderedNameSpanAsserter<ChildAsserter>.(match: MatchResult) -> Unit,
    ) {
        val effectiveOpen = open ?: this@DefaultOrderedNameAsserter.open
        with(sourceAsserter) {
            (this@DefaultOrderedNameAsserter.currentChildSpanIndex++) {
                val match = assertName(this@invoke)
                val asserter = DefaultOrderedNameSpanAsserter(this, effectiveOpen).also { block(it, match) }
                if (!effectiveOpen) {
                    asserter.assertNoMoreChildren()
                }
            }
        }
    }

    override fun toString(): String {
        return "DefaultOrderedNameAsserter(open=$open, source=$sourceAsserter)"
    }

    open class DefaultOrderedNameSpanAsserter<
            out ChildAsserter : SpanDataAsserter<ChildAsserter>,
            >(
        sourceAsserter: ChildAsserter,
        open: Boolean,
    ) : DefaultOrderedNameAsserter<ChildAsserter, ChildAsserter>(sourceAsserter, open),
            SpanDataContainerAsserter.OrderedNameAsserter.OrderedNameSpanAsserter<ChildAsserter> {

        override val data: SpanData
            get() = sourceAsserter.data

        override fun toString(): String {
            return "DefaultOrderedNameSpanAsserter(open=$open, source=$sourceAsserter)"
        }
    }
}
