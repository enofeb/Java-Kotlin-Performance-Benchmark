package benchmarks.kotlinconverted

/*
 * The Computer Language Benchmarks Game
 * https://salsa.debian.org/benchmarksgame-team/benchmarksgame/
 *
 * Contributed by Oleg Mazurov, June 2010
 *
 */

import benchmarkhandle.*
import benchmarks.BaseBenchmark
import java.util.concurrent.atomic.AtomicInteger

object FannkuchRedux:BaseBenchmark(){

    @JvmStatic
    fun main(args: Array<String>) {
        val fannkuchRedux=FannkuchRedux
        val benchmarkHandler=BenchmarkHandler()
        val benchmarkMessage=BenchmarkMessage(BenchmarkType.FANNKUCH_REDUX, BenchmarkImplementation.KOTLIN_CONVERTED,BenchmarkMetric.MEMORY_CONSUMPTION)
        for (i in 0 until 100) {
            benchmarkHandler.startMeasuringMetrics(benchmarkMessage , fannkuchRedux, args)
        }
    }
    fun fannkuch(n: Int): Int {
        val perm = IntArray(n)
        val perm1 = IntArray(n)
        val count = IntArray(n)
        var maxFlipsCount = 0
        var permCount = 0
        var checksum = 0

        for (i in 0 until n) perm1[i] = i
        var r = n

        while (true) {

            while (r != 1) {
                count[r - 1] = r
                r--
            }

            for (i in 0 until n) perm[i] = perm1[i]
            var flipsCount = 0
            var k: Int

            //Assignments can not be used directly like JAVA
            while ((perm[0].let { k=it;it!=0})) {
                val k2 = k + 1 shr 1
                for (i in 0 until k2) {
                    val temp = perm[i]
                    perm[i] = perm[k - i]
                    perm[k - i] = temp
                }
                flipsCount++
            }

            maxFlipsCount = Math.max(maxFlipsCount, flipsCount)
            checksum += if (permCount % 2 == 0) flipsCount else -flipsCount

            // Use incremental change to generate another permutation
            while (true) {
                if (r == n) {
                    println(checksum)
                    return maxFlipsCount
                }
                val perm0 = perm1[0]
                var i = 0
                while (i < r) {
                    val j = i + 1
                    perm1[i] = perm1[j]
                    i = j
                }
                perm1[r] = perm0

                count[r] = count[r] - 1
                if (count[r] > 0) break
                r++
            }

            permCount++
        }
    }
    override fun initAlgorithm(args: Array<String>) {
        var n = 7
        if (args.size > 0) n = Integer.parseInt(args[0])
        println("Pfannkuchen(" + n + ") = " + fannkuch(n))
    }
}