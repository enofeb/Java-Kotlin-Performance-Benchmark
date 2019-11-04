package benchmarks.kotlinconverted

/* The Computer Language Benchmarks Game
   https://salsa.debian.org/benchmarksgame-team/benchmarksgame/

   modified by Henco Appel
*/

import java.io.*
import java.util.concurrent.atomic.*
import java.util.stream.*

internal object Mandelbrot {

    fun getByte(Crb: DoubleArray, CibY: Double, x: Int): Byte {
        var res = 0
        var i = 0
        while (i < 8) {
            var Zr1 = Crb[x + i]
            var Zi1 = CibY

            var Zr2 = Crb[x + i + 1]
            var Zi2 = CibY

            var b = 0
            var j = 49
            do {
                val nZr1 = Zr1 * Zr1 - Zi1 * Zi1 + Crb[x + i]
                Zi1 = Zr1 * Zi1 + Zr1 * Zi1 + CibY
                Zr1 = nZr1

                val nZr2 = Zr2 * Zr2 - Zi2 * Zi2 + Crb[x + i + 1]
                Zi2 = Zr2 * Zi2 + Zr2 * Zi2 + CibY
                Zr2 = nZr2

                if (Zr1 * Zr1 + Zi1 * Zi1 > 4) {
                    b = b or 2
                    if (b == 3) break
                }
                if (Zr2 * Zr2 + Zi2 * Zi2 > 4) {
                    b = b or 1
                    if (b == 3) break
                }
            } while (--j > 0)
            res = (res shl 2) + b
            i += 2
        }
        return (res xor -1).toByte()
    }

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        var N = 6000
        if (args.size >= 1)
            N = Integer.parseInt(args[0])

        val Crb = DoubleArray(N + 7)
        val invN = 2.0 / N
        for (i in 0 until N) {
            Crb[i] = i * invN - 1.5
        }
        val lineLen = (N - 1) / 8 + 1
        val data = ByteArray(N * lineLen)
        IntStream.range(0, N).parallel().forEach { y ->
            val Ciby = y * invN - 1.0
            val offset = y * lineLen
            for (x in 0 until lineLen)
                data[offset + x] = getByte(Crb, Ciby, x * 8)
        }

        val stream = BufferedOutputStream(System.out)
        stream.write("P4\n$N $N\n".toByteArray())
        stream.write(data)
        stream.close()
    }
}