package benchmarks.kotlinpure

object BinaryTree {

    private val minDepth = 4
    //private lateinit var treeNode:TreeNode


    @JvmStatic
    fun main(args: Array<String>) {

        var n = 0
        if (args.size > 0) n = Integer.parseInt(args[0])

        val maxDepth = if (minDepth + 2 > n) minDepth + 2 else n
        val stretchDepth = maxDepth + 1


        var check = TreeNode.bottomUpTree(stretchDepth).itemCheck()
        println("stretch tree of depth $stretchDepth\t check: $check")

        val longLivedTree = TreeNode.bottomUpTree(maxDepth)

        var depth = minDepth
        while (depth <= maxDepth) {
            val iterations = 1 shl maxDepth - depth + minDepth
            check = 0

            for (i in 1..iterations) {
                check += TreeNode.bottomUpTree(depth).itemCheck()
            }
            println("$iterations\t trees of depth $depth\t check: $check")
            depth += 2
        }
        println("long lived tree of depth " + maxDepth + "\t check: " + longLivedTree.itemCheck())

    }


    private class TreeNode internal constructor(private val left: TreeNode?, private val right: TreeNode?) {

        companion object {
            internal fun bottomUpTree(depth: Int): TreeNode =
                if (depth > 0) TreeNode(bottomUpTree(depth - 1), bottomUpTree(depth - 1)) else TreeNode(null, null)
        }

        internal fun itemCheck(): Int = if (left == null) 1 else 1 + left.itemCheck() + right!!.itemCheck()
    }
}