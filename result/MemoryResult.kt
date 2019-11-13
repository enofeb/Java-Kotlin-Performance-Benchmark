package result

class MemoryResult( val mBytesPerObject: Long,val mAllocationSizeTotal: Long, val mAllocationSizeFree: Long):BaseBenchmarkResult(){
    override fun resultsToString(): String {
        val resultString = "Actual Used Memory: $mBytesPerObject, Before Memory: $mAllocationSizeTotal, After Memory:$mAllocationSizeFree"
        return resultString
    }
}

