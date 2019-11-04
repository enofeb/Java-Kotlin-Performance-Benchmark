package result

class ExecutionTimeResult(val executionTime:Long):BaseBenchmarkResult(){
    //NANOTIME TYPE RESULT
    override fun resultsToString(): String {
        val resultString = "$executionTime"
        return resultString
    }
}