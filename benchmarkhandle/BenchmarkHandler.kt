package benchmarkhandle

import result.BaseBenchmarkResult
import result.ExecutionTimeResult
import result.MemoryResult
import result.ConvertResultToFile
import benchmarks.BaseBenchmark

class BenchmarkHandler {

    private val convertResultToFile = ConvertResultToFile()

    private var incrementIndex = 1

    private var executionAverage=1

    private var resultList: MutableList<BaseBenchmarkResult>? = mutableListOf<BaseBenchmarkResult>()

    private var exeTimeList: MutableList<Long>? = mutableListOf()
    private var memoryList: MutableList<Long>? = mutableListOf()

    fun startMeasuringMetrics(benchmarkMessage: BenchmarkMessage, baseBenchmark: BaseBenchmark, args: Array<String>) {
        var benchmarkResult: BaseBenchmarkResult? = null
        when (benchmarkMessage.metric) {
            BenchmarkMetric.EXECUTIONTIME -> {
                val startTime = System.currentTimeMillis()
                baseBenchmark.initAlgorithm(args)
                val endTime = System.currentTimeMillis()
                val executionTime = endTime.minus(startTime)
                exeTimeList!!.add(executionTime)
                benchmarkResult = ExecutionTimeResult(executionTime, incrementIndex++)
                resultList!!.add(benchmarkResult)
                triggerBenchmark(benchmarkMessage.type,benchmarkMessage.implementation,resultList!!,benchmarkResult,exeTimeList!!)
            }
            BenchmarkMetric.MEMORY_CONSUMPTION -> {
                // get the total memory
                val beforeUsedMem: Long
                val afterUsedMem: Long
                val actualMemUsed: Long
                beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
                baseBenchmark.initAlgorithm(args)
                afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
                actualMemUsed = afterUsedMem - beforeUsedMem
                memoryList!!.add(actualMemUsed)
                benchmarkResult = MemoryResult(actualMemUsed, beforeUsedMem, afterUsedMem, incrementIndex++)
                resultList!!.add(benchmarkResult)
                triggerBenchmark(benchmarkMessage.type,benchmarkMessage.implementation,resultList!!,benchmarkResult,memoryList!!)
            }
            BenchmarkMetric.GARBAGE_COLLECTION -> {
                baseBenchmark.initAlgorithm(args)
                System.gc()
                GcLogUtil.startLoggingGc()
            }
        }
    }

    private fun triggerBenchmark(
        benchmarkType: BenchmarkType,
        benchmarkImplementation: BenchmarkImplementation,
        resultList: MutableList<BaseBenchmarkResult>,
        benchmarkResult: BaseBenchmarkResult,
        exeList: MutableList<Long>
    ) {
        when (benchmarkType) {
            BenchmarkType.BINARYTREE -> {
                when (benchmarkImplementation) {
                    BenchmarkImplementation.JAVA -> {
                        convertResultToFile.writeResultToFile(resultList!!, "Binary Tree Java", benchmarkResult,exeList)
                    }
                    BenchmarkImplementation.KOTLIN_CONVERTED -> {
                       // convertResultToFile.writeResultToFile(resultList!!, "Binary Tree Kotlin-Converted", benchmarkResult)
                    }
                    BenchmarkImplementation.KOTLIN_PURE -> {
                      //  convertResultToFile.writeResultToFile(resultList!!, "Binary Tree Kotlin-Pure", benchmarkResult)
                    }
                }
            }
            BenchmarkType.FANNKUCH_REDUX -> {
                when (benchmarkImplementation) {
                    BenchmarkImplementation.JAVA -> {
                      //  convertResultToFile.writeResultToFile(resultList!!, "Fannkuch Redux Java", benchmarkResult)
                    }
                    BenchmarkImplementation.KOTLIN_CONVERTED -> {
                      //  convertResultToFile.writeResultToFile(resultList!!, "Fannkuch Redux Kotlin-Converted", benchmarkResult)
                    }
                    BenchmarkImplementation.KOTLIN_PURE -> {
                      //  convertResultToFile.writeResultToFile(resultList!!, "Fannkuch Redux Kotlin-Pure", benchmarkResult)
                    }
                }
            }
            BenchmarkType.MANDELBROT -> {
                when (benchmarkImplementation) {
                    BenchmarkImplementation.JAVA -> {
                      //  convertResultToFile.writeResultToFile(resultList!!, "Mandelbrot Java", benchmarkResult)
                    }
                    BenchmarkImplementation.KOTLIN_CONVERTED -> {
                    //    convertResultToFile.writeResultToFile(resultList!!, "Mandelbrot Kotlin Converted", benchmarkResult)
                    }
                    BenchmarkImplementation.KOTLIN_PURE -> {
                    //    convertResultToFile.writeResultToFile(resultList!!, "Mandelbrot Kotlin Pure", benchmarkResult)
                    }
                }
            }
            BenchmarkType.REGEX_REDUX -> {
                when (benchmarkImplementation) {
                    BenchmarkImplementation.JAVA -> {
                     //   convertResultToFile.writeResultToFile(resultList!!, "Regex Redux Java", benchmarkResult)
                    }
                    BenchmarkImplementation.KOTLIN_CONVERTED -> {
                       // convertResultToFile.writeResultToFile(resultList!!, "Regex Redux Kotlin Converted", benchmarkResult)
                    }
                    BenchmarkImplementation.KOTLIN_PURE -> {
                      //  convertResultToFile.writeResultToFile(resultList!!, "Regex Redux Kotlin Pure", benchmarkResult)
                    }
                }
            }
        }
    }

}