package result

import benchmarkhandle.GCData
import java.io.File
import java.io.FileWriter
import java.io.IOException

class ConvertResultToFile {

    var averageDuration:MutableList<Long>?= mutableListOf()
    var averageSum:MutableList<Long>?=mutableListOf()

    fun writeResultToFile(
        results: MutableList<BaseBenchmarkResult>,
        fileName: String,
        benchmarkResult: BaseBenchmarkResult,
        average: MutableList<Long>
    ) {
        try {
            val filePath = File("/Users/enes/Documents")
            val fileResult = File(filePath, fileName)
            val fileWriter = FileWriter(fileResult)

            attachResultToFile(results, fileWriter, fileName, benchmarkResult,average)

            fileWriter.flush()
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun attachResultToFile(
        result: MutableList<BaseBenchmarkResult>,
        fileWriter: FileWriter,
        fileName: String,
        benchmarkResult: BaseBenchmarkResult,
        average: MutableList<Long>
    ) {

        var resultType = benchmarkResult
        var dataFormat: String = ""

        print(average.average().toString())

        when (resultType) {
            is ExecutionTimeResult -> {
                dataFormat = "[Execution Time (milliseconds)]"
            }
            is MemoryResult -> {
                dataFormat = "[Actual Used Memory(byte), Before Memory(byte), After Memory(byte)]"
            }
            is GarbageCollectorResult -> {
                dataFormat = "[GC Start Time, GC End Time, GC Duration, GC Before Memory(mb),GC After Memory(MB)]"
            }
        }

        fileWriter.append("Benchmark results for $fileName \n")
        fileWriter.append("RESULTS $dataFormat  \n")

        for (i in 0 until result.size) {
            fileWriter.apply {
                append(result[i].resultsToString())
                append(System.getProperty("line.separator"))
            }
        }
        fileWriter.append("AVERAGE: ${average.average()}  \n")
    }

    fun writeGCResultToFile(
        results: MutableList<GCData>,
        fileName: String) {
        try {
            val filePath = File("/Users/enes/Documents")
            val fileResult = File(filePath, fileName)
            val fileWriter = FileWriter(fileResult)

            attachGCResultToFile(results, fileWriter, fileName)

            fileWriter.flush()
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun attachGCResultToFile(
        result: MutableList<GCData>,
        fileWriter: FileWriter,
        fileName: String) {

        var dataFormat: String = ""


        dataFormat = "[GC Start Time, GC End Time, GC Duration, GC Before Memory(mb),GC After Memory(MB)]"

        fileWriter.append("Benchmark results for $fileName \n")
        fileWriter.append("RESULTS $dataFormat  \n")

        for (i in 0 until result.size) {
            fileWriter.apply {
                append("(${result[i].startTime},${result[i].endTime},${result[i].duration},${result[i].beforeMemory},${result[i].afterMemory},${result[i].memorySum})")
                append(System.getProperty("line.separator"))
            }
        }
        result.iterator().forEach { gcData ->
            averageDuration!!.add(gcData.duration)
            averageSum!!.add(gcData.memorySum)

        }
        fileWriter.append("DURATION AVERAGE: ${averageDuration!!.average()}  \n")
        fileWriter.append("MEMORY AVERAGE: ${averageSum!!.average()}  \n")
    }
}