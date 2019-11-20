package result

class MemoryResult( val actualMemorySize: Long,val beforeMemorySize: Long, val afterMemorySize: Long,val index:Int):BaseBenchmarkResult(){
    override fun resultsToString(): String {
        val resultString = "$index.($actualMemorySize,$beforeMemorySize,$afterMemorySize)"
        return resultString
    }
}

