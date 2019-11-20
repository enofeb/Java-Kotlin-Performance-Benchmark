package result

class ExecutionTimeResult(val executionTime:Long,val index:Int):BaseBenchmarkResult(){
    //MILLISECOND TYPE RESULT
    override fun resultsToString(): String {
        val resultString = "$index.($executionTime)"
        return resultString
    }
}