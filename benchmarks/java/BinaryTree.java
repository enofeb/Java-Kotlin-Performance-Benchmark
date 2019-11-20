package benchmarks.java;


import benchmarkhandle.*;
import benchmarks.BaseBenchmark;


public class BinaryTree extends BaseBenchmark {

    private final static int minDepth = 8;


    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        BenchmarkHandler benchmarkHandler = new BenchmarkHandler();
        BenchmarkMessage benchmarkMessage = new BenchmarkMessage(BenchmarkType.BINARYTREE, BenchmarkImplementation.JAVA, BenchmarkMetric.GARBAGE_COLLECTION);
        for (int i = 0; i < 10; i++) {
            benchmarkHandler.startMeasuringMetrics(benchmarkMessage, binaryTree, args);
        }
    }


    public void initAlgorithm(String[] args) {

        int n = 0;
        if (args.length > 0) n = Integer.parseInt(args[0]);

        // Comparison happen,bigger number will pick up
        int maxDepth = Math.max(minDepth + 2, n);
        //to find max depth +1
        int stretchDepth = maxDepth + 1;

        int check = (TreeNode.bottomUpTree(stretchDepth)).itemCheck();
        System.out.println("stretch tree of depth " + stretchDepth + "\t check: " + check);

        TreeNode longLivedTree = TreeNode.bottomUpTree(maxDepth);

        for (int depth = minDepth; depth <= maxDepth; depth += 2) {
            int iterations = 1 << ((maxDepth - depth) + minDepth);
            check = 0;

            for (int i = 1; i <= iterations; i++) {
                check += (TreeNode.bottomUpTree(depth)).itemCheck();
            }
            System.out.println(iterations + "\t trees of depth " + depth + "\t check: " + check);
        }
        System.out.println("long lived tree of depth " + maxDepth + "\t check: " + longLivedTree.itemCheck());
    }

    private static class TreeNode {
        private TreeNode left, right;

        TreeNode(TreeNode left, TreeNode right) {
            this.left = left;
            this.right = right;
        }

        //Here is the recursive loop
        private static TreeNode bottomUpTree(int depth) {
            if (depth > 0) {
                return new TreeNode(
                        bottomUpTree(depth - 1)
                        , bottomUpTree(depth - 1)
                );

            } else {
                return new TreeNode(null, null);
            }
        }

        private int itemCheck() {
            // if necessary deallocate here
            if (left == null) return 1;
            else return 1 + left.itemCheck() + right.itemCheck();
        }
    }
}

