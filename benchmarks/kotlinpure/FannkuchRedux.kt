package benchmarks.kotlinpure
/*
 * The Computer Language Benchmarks Game
 * https://salsa.debian.org/benchmarksgame-team/benchmarksgame/
 *
 * Contributed by Oleg Mazurov, June 2010
 *
 */

import benchmarkhandle.*
import benchmarks.BaseBenchmark

object FannkuchRedux:BaseBenchmark(){

    @JvmStatic
    fun main(args: Array<String>) {
        val fannkuchRedux=FannkuchRedux
        val benchmarkHandler= BenchmarkHandler()
        val benchmarkMessage=
            BenchmarkMessage(BenchmarkType.BINARYTREE, BenchmarkImplementation.KOTLIN_PURE,BenchmarkMetric.MEMORY_CONSUMPTION)
      //  for (i in 0 until 1300){
            benchmarkHandler.startMeasuringMetrics(benchmarkMessage, fannkuchRedux, args)
       // }
    }
    fun fannkuch(n: Int): Pair<Int, Int> {
        val permutation = IntArray(n) { it } //Ascending array of n elements
        val flipPerm = IntArray(n) //The permutation to be flipped
        val depthCount = IntArray(n)
        var maxFlipCount = 0
        var checksum = 0
        var permCount = 0
        var depth = n

        while (true) {

            while (depth != 1) {
                depthCount[depth - 1] = depth
                depth--
            }

            for (i in 0 until n) flipPerm[i] = permutation[i]
            var rotationCount = 0
            var k: Int = flipPerm[0]

            //Rotate first k elements until k = 0
            while (k != 0) {
                flipPerm.take(k + 1)
                    .reversed()
                    .forEachIndexed{idx, value -> flipPerm[idx] = value}
                k = flipPerm[0]
                rotationCount++
            }

            //Update maxRotationCount and checksum
            if (rotationCount > maxFlipCount) maxFlipCount = rotationCount
            checksum += if (permCount % 2 == 0) rotationCount else -rotationCount

            //Generate another permutation using incremental change
            while (true) {
                if (depth == n) {
                    return Pair(checksum, maxFlipCount)
                }
                val leftMostDigit: Int = permutation[0]

                //Perform rotation on this level
                var i = 0
                while (i < depth) {
                    val j = i + 1
                    permutation[i] = permutation[j]
                    i++
                }
                permutation[depth] = leftMostDigit

                depthCount[depth] = depthCount[depth] - 1
                if (depthCount[depth] > 0) break //Calculate rotations if more permutations on this level, else continue to next level
                depth++
            }

            permCount++
        }
    }

    override fun initAlgorithm(args: Array<String>) {
        //  val n: Int = if (args.isNotEmpty()) args[0].toInt() else 7
        val (checksum, maxRotations) = FannkuchRedux.fannkuch(7)
        println("$checksum\nPfannkuchen(7) = $maxRotations")
    }
}