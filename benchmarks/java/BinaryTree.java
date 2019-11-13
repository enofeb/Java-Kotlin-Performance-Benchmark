package benchmarks.java;


import benchmarkhandle.BenchmarkHandler;
import benchmarkhandle.BenchmarkMetric;
import benchmarks.BaseBenchmark;

import java.util.logging.LogRecord;

public class BinaryTree extends BaseBenchmark {

    private final static int minDepth = 8;

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        BinaryTree binaryTreeMem = new BinaryTree();

        BenchmarkHandler benchmarkHandler = new BenchmarkHandler();
        //Allocate in memory heap
        BenchmarkHandler benchmarkHandlerMem = new BenchmarkHandler();

        for (int i = 0; i < 10; i++) {
            benchmarkHandler.startMeasuringMetrics(BenchmarkMetric.EXECUTIONTIME, binaryTree, args);
        }
        // System.gc();
        //  benchmarkHandlerMem.startMeasuringMetrics(BenchmarkMetric.MEMORY_CONSUMPTION,binaryTreeMem,args);

    }


    public void initAlgorithm(String[] args) {
        // long startTime,endTime;
        //  startTime=System.nanoTime();
        //    long beforeUsedMem,afterUsedMem,actualMemUsed;
        //    beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        int n = 0;
        if (args.length > 0) n = Integer.parseInt(args[0]);

        // Comparison happen,bigger number will pick up
        int maxDepth = Math.max(minDepth + 2, n);
        //to find max depth +1
        int stretchDepth = maxDepth + 1;

        int check = (TreeNode.bottomUpTree(stretchDepth)).itemCheck();
        // System.out.println("stretch tree of depth "+stretchDepth+"\t check: " + check);
        ////////////////////////////////CUT BEFORE

        TreeNode longLivedTree = TreeNode.bottomUpTree(maxDepth);

        for (int depth = minDepth; depth <= maxDepth; depth += 2) {
            int iterations = 1 << ((maxDepth - depth) + minDepth);
            check = 0;

            for (int i = 1; i <= iterations; i++) {
                check += (TreeNode.bottomUpTree(depth)).itemCheck();
            }
            //  System.out.println(iterations + "\t trees of depth " + depth + "\t check: " + check);
        }
        //  System.out.println("long lived tree of depth " + maxDepth + "\t check: "+ longLivedTree.itemCheck());

        //  afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        //   actualMemUsed=afterUsedMem-beforeUsedMem;
        //    endTime=System.nanoTime();
        //  System.out.println(actualMemUsed);

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

