package benchmarkhandle


data class BenchmarkMessage(val type: BenchmarkType, val implementation: BenchmarkImplementation,
                            val metric: BenchmarkMetric, val data: Any?)

enum class BenchmarkType {
    BINARYTREE, FANNKUCH_REDUX, MANDELBROT, REGEX_REDUX
}

enum class BenchmarkImplementation {
    JAVA, KOTLIN_CONVERTED, KOTLIN_PURE
}

enum class BenchmarkMetric{
    GARBAGE_COLLECTION, MEMORY_CONSUMPTION, EXECUTIONTIME
}

enum class BenchmarkStatus(val id: Int){
    START_BENCHMARK(1),
    BENCHMARK_FINISHED(0),
    UPDATE_PROGRESS(2),
    UPDATE_MESSAGE(3)
}