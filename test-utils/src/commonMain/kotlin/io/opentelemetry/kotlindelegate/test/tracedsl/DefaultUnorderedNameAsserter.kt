package io.opentelemetry.kotlindelegate.test.tracedsl

import io.opentelemetry.kotlindelegate.test.SpanData
import io.opentelemetry.kotlindelegate.test.SpanMatchingStrategy
import kotlin.test.fail

open class DefaultUnorderedNameAsserter<
        out ChildAsserter : SpanDataAsserter<ChildAsserter>,
        out BaseAsserter : SpanDataContainerAsserter<ChildAsserter>,
        >(
    protected val sourceAsserter: BaseAsserter,
) : SpanDataContainerAsserter.UnorderedNameAsserter<ChildAsserter, BaseAsserter> {

    override val path: String
        get() = sourceAsserter.path

    override fun <T> baseAsserter(block: BaseAsserter.() -> T): T = sourceAsserter.block()

    override val directChildren: Sequence<SpanDataContainerAsserter.UnorderedNameAsserter.UnorderedNameSpanAsserter<ChildAsserter>>
        get() = sourceAsserter.directChildren.map { DefaultUnorderedNameSpanAsserter(it) }

    override fun String.invoke(
        required: Boolean,
        matchingStrategy: SpanMatchingStrategy,
        block: SpanDataContainerAsserter.UnorderedNameAsserter.UnorderedNameSpanAsserter<ChildAsserter>.() -> Unit,
    ) {
        val dChildren = sourceAsserter.directChildren.toList()
        val spanDataAsserters = dChildren.filter { it.data.name == this }
        val strategyAsserters = matchingStrategy.filter(spanDataAsserters)
        if (required && strategyAsserters.isEmpty()) {
            fail("No direct child matching name '$this' found at ${sourceAsserter.path}: ${dChildren.map { it.data.name }}")
        } else {
            strategyAsserters.forEach { block(DefaultUnorderedNameSpanAsserter(it)) }
        }
    }

    override operator fun Regex.invoke(
        required: Boolean,
        matchingStrategy: SpanMatchingStrategy,
        block: SpanDataContainerAsserter.UnorderedNameAsserter.UnorderedNameSpanAsserter<ChildAsserter>.(match: MatchResult) -> Unit,
    ) {
        val dChildren = sourceAsserter.directChildren.toList()
        val spanDataAsserters = dChildren.mapNotNull { child -> matchEntire(child.data.name)?.let { child to it } }
        val strategyAsserters = matchingStrategy.filter(spanDataAsserters)
        if (required && strategyAsserters.isEmpty()) {
            fail("No direct child matching regex '$this' found at ${sourceAsserter.path}: ${dChildren.map { it.data.name }}")
        } else {
            strategyAsserters.forEach { block(DefaultUnorderedNameSpanAsserter(it.first), it.second) }
        }
    }

    open class DefaultUnorderedNameSpanAsserter<
            out ChildAsserter : SpanDataAsserter<ChildAsserter>,
            >(
        sourceAsserter: ChildAsserter,
    ) : DefaultUnorderedNameAsserter<ChildAsserter, ChildAsserter>(sourceAsserter),
            SpanDataContainerAsserter.UnorderedNameAsserter.UnorderedNameSpanAsserter<ChildAsserter> {

        override val data: SpanData
            get() = sourceAsserter.data
    }
}
