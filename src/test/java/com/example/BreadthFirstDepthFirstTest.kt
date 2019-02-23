package com.example

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.concurrent.TimeUnit

class BreadthFirstDepthFirstTest {
    @field:[Rule JvmField]
    val timeoutRule = Timeout(100, TimeUnit.MILLISECONDS)

    private val expected = mutableListOf<String>()

    @Before
    fun doBefore() {
        expected.clear()
    }

    @Test
    fun printBreadthFirst() {
        expected
            .addThisAnd("A")
            .addThisAnd("E")
            .addThisAnd("D")
            .addThisAnd("F")
            .addThisAnd("C")
            .addThisAnd("B")

        val actual = mutableListOf<String>()
        BreadthFirstDepthFirst.printBreadthFirst(tree) { actual.add(it) }
        Truth.assertThat(actual).containsExactlyElementsIn(expected).inOrder()
    }

    @Test
    fun printDepthFirst() {
        expected
            .addThisAnd("A")
            .addThisAnd("E")
            .addThisAnd("F")
            .addThisAnd("D")
            .addThisAnd("C")
            .addThisAnd("B")

        val actual = mutableListOf<String>()
        BreadthFirstDepthFirst.printDepthFirst(tree) { actual.add(it) }
        Truth.assertThat(actual).containsExactlyElementsIn(expected).inOrder()
    }

    // Helper
    private inline fun <reified T>  MutableList<T>.addThisAnd(item: T): MutableList<T> {
        add(item)
        return this
    }

    companion object {
        val tree: FileOrDir
        init {
            val fileOrDirF = ExampleFileOrDir("F", false, emptyList())
            val fileOrDirE = ExampleFileOrDir("E", true, listOf(fileOrDirF))

            val fileOrDirC = ExampleFileOrDir("C", false, emptyList())
            val fileOrDirB = ExampleFileOrDir("B", false, emptyList())
            val fileOrDirD = ExampleFileOrDir("D", true, listOf(fileOrDirC, fileOrDirB))

            tree = ExampleFileOrDir("A", true, listOf(fileOrDirE, fileOrDirD))
            /*
            Create the tree so it looks like this:
                  A
                 / \
                E    D
                \   / \
                 F C   B
             */
        }
    }
}