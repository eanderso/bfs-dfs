package com.example

interface FileOrDir {
    val name: String
    val isDir: Boolean
    val children: List<FileOrDir>
}

data class ExampleFileOrDir(
    override val name: String,
    override val isDir: Boolean,
    override val children: List<FileOrDir>
) : FileOrDir

object BreadthFirstDepthFirst {
    /**
     * This will print the name of each file or directory under the $item in a breadth first manner.
     *
     * If there is an [item] that points to a previous [item] you could end up stuck in a loop.
     * Example: A -> B -> C -> A will loop until a stack overflow crash if the isDir flag is true for C
     *
     * @param item file or directory to start with
     * @param printFunction What you'd like to do with the
     */
    fun printBreadthFirst(item: FileOrDir, printFunction: (String) -> Unit) {
        printFunction.invoke(item.name)
        val nextNodes = java.util.LinkedList<FileOrDir>()
        var element: FileOrDir? = item

        while (element != null) {
            element.children.forEach { kid ->
                printFunction.invoke(kid.name)
                if (kid.isDir) {
                    nextNodes.addLast(kid)
                }
            }

            element = nextNodes.pollFirst()
        }
    }

    /**
     * This will print the name of each file or directory under the $item in a depth first manner.
     *
     * If there is an [item] that points to a previous [item] you could end up stuck in a loop.
     * Example: A -> B -> C -> A will loop until a stack overflow crash if the isDir flag is true for C
     *
     * @param item file or directory to start with
     * @param printFunction What you'd like to do with the
     */
    fun printDepthFirst(item: FileOrDir, printFunction: (String) -> Unit) {
        printFunction.invoke(item.name)
        item.children.forEach { kid ->
            if (kid.isDir) {
                printDepthFirst(kid, printFunction)
            } else {
                printFunction.invoke(kid.name)
            }
        }
    }
}
