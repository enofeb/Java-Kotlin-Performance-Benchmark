import result.BaseBenchmarkResult
import result.GarbageCollectorResult
import result.MemoryResult
import result.ExecutionTimeResult
import java.io.File
import java.io.FileWriter
import java.io.IOException

class ConvertResultToFile {

    private val warmup: Boolean = true


    //Variable number of arguments.. varargs parameter can be called using either an array or a list of values.
    fun writeResultToFile(benchmark:BaseBenchmarkResult, fileName: String){
       //val (results, fileName) = result.first()
         try {
            val filePath=File("/Users/enes/Documents")
            val fileResult=File(filePath,fileName)
            val fileWriter=FileWriter(fileResult)
           //  attachResultToFile(fileResult.absolutePath, results, fileWriter)
             fileWriter.apply {
                 append(benchmark.resultsToString())
             }
             fileWriter.flush()
             fileWriter.close()
         }catch(e: IOException) {
            e.printStackTrace()
         }
        val output = "$fileName->${benchmark.resultsToString()}"

        println(output)

    }

    fun attachResultToFile(fileName:String,results:BaseBenchmarkResult,writer:FileWriter){
        var hotResults = results

    //    if(warmup && results.size > 10){
      //      hotResults = results.drop(10) as ArrayList<BaseBenchmarkResult>
   //     }

        val resultType=hotResults
        var dataFormat: String = ""

        when(resultType){
            is GarbageCollectorResult ->dataFormat = "[Bytes Freed Per GC,Time per GC (ms),GC Count, Time (ms), Bytes freed]"
            is MemoryResult-> dataFormat = "[Bytes per object,Allocation Count, Allocation Size]"
            is ExecutionTimeResult-> dataFormat = "[Runtime (ms)]"
        }

        writer.apply {
            append("Benchmark results for $fileName \n")
            append("------------------------------------------------------------------------------\n")
            append("                  Raw data: $dataFormat \n")
            append("------------------------------------------------------------------------------\n")

         /*   if(warmup && results.size > 10){
                append("Discarded results (warmup)\n")
                results.take(10).forEachIndexed{idx, result ->
                    append("($idx, ${result})\n")
                }
                append("------------------------------------------------------------------------------\n")
                append("Warmuped results:\n")
        }*/

     //   hotResults.forEachIndexed{ idx, result ->
       //     append("($idx, ${result.resultsToString()})\n")
        }
    //    append("------------------------------------------------------------------------------\n")

    }
}