package benchmarks.kotlinpure

import benchmarkhandle.*
import benchmarks.BaseBenchmark

object BinaryTree : BaseBenchmark() {

    private val minDepth = 8

    @JvmStatic
    fun main(args: Array<String>) {
        val binaryTree = BinaryTree
        val benchmarkHandler = BenchmarkHandler()
        val benchmarkMessage= BenchmarkMessage(BenchmarkType.BINARYTREE, BenchmarkImplementation.KOTLIN_PURE,BenchmarkMetric.MEMORY_CONSUMPTION)

        for (i in 0 until 10) {
            benchmarkHandler.startMeasuringMetrics(benchmarkMessage, binaryTree, args)
        }
    }

    override fun initAlgorithm(args: Array<String>) {
        var n = 0
        if (args.isNotEmpty()) n = Integer.parseInt(args[0])

        val maxDepth = if (n < minDepth + 2) minDepth + 2 else n
        val stretchDepth = maxDepth + 1


        var check = bottomUpTree(stretchDepth).itemCheck()
        println("stretch tree of depth $stretchDepth\t check: $check")

        val longLivedTree = bottomUpTree(maxDepth)

        var depth = minDepth
        while (depth <= maxDepth) {
            val iterations = 1 shl maxDepth - depth + minDepth
            check = 0

            for (i in 1 until iterations) {
                check += bottomUpTree(depth).itemCheck()
            }
            println("$iterations\t trees of depth $depth\t check: $check")
            depth += 2
        }
        println("long lived tree of depth " + maxDepth + "\t check: " + longLivedTree.itemCheck())
    }

    private fun bottomUpTree(depth: Int): TreeNode {
        return if (0 < depth) {
            TreeNode(bottomUpTree(depth - 1), bottomUpTree(depth - 1))
        } else TreeNode()
    }

    private data class TreeNode(
        val left: TreeNode? = null,
        val right: TreeNode? = null
    ) {
        fun itemCheck(): Int = if (null == left) 1 else 1 + left.itemCheck() + right!!.itemCheck()
    }
}