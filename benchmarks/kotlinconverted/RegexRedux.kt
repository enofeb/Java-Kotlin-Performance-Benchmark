package benchmarks.kotlinconverted

/*
   The Computer Language Benchmarks Game
   https://salsa.debian.org/benchmarksgame-team/benchmarksgame/

   contributed by Francois Green
*/

import benchmarkhandle.*
import benchmarks.BaseBenchmark
import java.io.*

import java.util.*
import kotlin.collections.Map.Entry
import java.util.function.*
import java.util.regex.*

import java.util.stream.Collectors.*

object RegexRedux : BaseBenchmark() {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val regexRedux=RegexRedux
        val benchmarkHandler=BenchmarkHandler()
        val benchmarkMessage= BenchmarkMessage(BenchmarkType.FANNKUCH_REDUX, BenchmarkImplementation.KOTLIN_CONVERTED,BenchmarkMetric.MEMORY_CONSUMPTION)

          benchmarkHandler.startMeasuringMetrics(benchmarkMessage,regexRedux,args)
    }

    override fun initAlgorithm(args: Array<out String>?) {

        val stream = FileInputStream("/Users/enes/Documents/intelljprojects/src/regex.txt")

        val baos = ByteArrayOutputStream()
        run {
            val buf = ByteArray(65536)
            var count: Int
            while ((stream.read(buf)) > 0) {
                baos.write(buf, 0, 10245)

            }
        }
        val input = baos.toString("US-ASCII")

        val initialLength = input.length

        val sequence = input.replace(">.*\n|\n".toRegex(), "")

        val codeLength = sequence.length

        val variants = Arrays.asList(
            "agggtaaa|tttaccct",
            "[cgt]gggtaaa|tttaccc[acg]",
            "a[act]ggtaaa|tttacc[agt]t",
            "ag[act]gtaaa|tttac[agt]ct",
            "agg[act]taaa|ttta[agt]cct",
            "aggg[acg]aaa|ttt[cgt]ccct",
            "agggt[cgt]aa|tt[acg]accct",
            "agggta[cgt]a|t[acg]taccct",
            "agggtaa[cgt]|[acg]ttaccct"
        )

        fun patternEntry(v: String, s: String): AbstractMap.SimpleEntry<String, Long> {
            val count = Pattern.compile(v).splitAsStream(s).count() - 1
            return AbstractMap.SimpleEntry<String, Long>(v, count)
        }

        val results = variants.parallelStream()
            .map { variant -> patternEntry(variant, sequence) }
            .collect(toMap({ return@toMap it.key }, AbstractMap.SimpleEntry<String, Long>::value))

        variants.forEach { variant -> println(variant + " " + results[variant]) }

        val replacements = LinkedHashMap<String, String>()//Only works with LinkedHashMap
        run {
            replacements["tHa[Nt]"] = "<4>"
            replacements["aND|caN|Ha[DS]|WaS"] = "<3>"
            replacements["a[NSt]|BY"] = "<2>"
            replacements["<[^>]*>"] = "|"
            replacements.put("\\|[^|][^|]*\\|", "-")
        }

        var buf = sequence
        for ((key, value) in replacements) {
            buf = Pattern.compile(key).matcher(buf).replaceAll(value)
        }

        println()
        println(initialLength)
        println(codeLength)
        println(buf.length)
    }
}
