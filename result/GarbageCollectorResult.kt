package result

class GarbageCollectorResult(val gcBytesFreedPerGC: Double, val gcTimePerGC: Double,val gcCount: Int, val gcTime: Int, val gcBytesFreed: Long):BaseBenchmarkResult(){
    override fun resultsToString(): String {
        val resultString = "$gcCount, $gcTime, $gcBytesFreed, $gcBytesFreedPerGC, $gcTimePerGC"
        return resultString
    }
}