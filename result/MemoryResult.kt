package result

class MemoryResult( val mBytesPerObject: Long,val mAllocationSizeTotal: Long, val mAllocationSizeFree: Long):BaseBenchmarkResult(){
    override fun resultsToString(): String {
        val resultString = "$mBytesPerObject, $mAllocationSizeTotal,$mAllocationSizeFree,"
        return resultString
    }
}

