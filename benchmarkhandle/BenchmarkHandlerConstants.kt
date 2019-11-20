package benchmarkhandle

import java.time.Duration


data class BenchmarkMessage(val type: BenchmarkType, val implementation: BenchmarkImplementation,
                            val metric: BenchmarkMetric)

data class GCData(val startTime: Long, val endTime: Long,val duration: Long,val beforeMemory:Long,val afterMemory:Long,val memorySum:Long)

enum class BenchmarkType {
    BINARYTREE, FANNKUCH_REDUX, MANDELBROT, REGEX_REDUX
}

enum class BenchmarkImplementation {
    JAVA, KOTLIN_CONVERTED, KOTLIN_PURE
}

enum class BenchmarkMetric{
    GARBAGE_COLLECTION, MEMORY_CONSUMPTION, EXECUTIONTIME
}