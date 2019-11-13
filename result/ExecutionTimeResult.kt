package result

class ExecutionTimeResult(val executionTime:Long):BaseBenchmarkResult(){
    //NANOTIME TYPE RESULT
    override fun resultsToString(): String {
        val resultString = "Execution Time Sum:$executionTime"
        return resultString
    }
}