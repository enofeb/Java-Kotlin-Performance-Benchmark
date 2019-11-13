package benchmarkhandle

import jdk.nashorn.api.tree.DebuggerTree
import result.BaseBenchmarkResult
import result.ExecutionTimeResult
import result.MemoryResult
import ConvertResultToFile
import benchmarks.BaseBenchmark
import javax.swing.DebugGraphics
import benchmarks.java.Mandelbrot as MandelbrotJava
import benchmarks.kotlinconverted.Mandelbrot as MandelbrotKotlinConverted
import benchmarks.kotlinpure.Mandelbrot as MandelbrotKotlinPure


import benchmarks.java.FannkuchRedux as FannkuchReduxJava
import benchmarks.kotlinconverted.FannkuchRedux as FannkuchReduxKotlinConverted
import benchmarks.kotlinpure.FannkuchRedux as FannkuchReduxKotlinPure

import benchmarks.java.BinaryTree as BinaryTreeJava
import benchmarks.kotlinconverted.BinaryTree as BinaryTreeKotlinConverted
import benchmarks.kotlinpure.BinaryTree as BinaryTreeKotlinPure


class BenchmarkHandler {
    private val binaryTreeJava=BinaryTreeJava()
    private val binaryTreeKotlinConverted=BinaryTreeKotlinConverted
    private val binaryTreeKotlinPure=BinaryTreeKotlinPure

    private val fannkuchReduxJava=FannkuchReduxJava()
    private val fannkuchReduxKotlinConverted=FannkuchReduxKotlinConverted
    private val fannkuchReduxKotlinPure=FannkuchReduxKotlinPure

    private val mandelbrotJava= MandelbrotJava()
    private val mandelbrotKotlinConverted= MandelbrotKotlinConverted
    private val mandelbrotKotlinPure= MandelbrotKotlinPure

    private val convertResultToFile= ConvertResultToFile()

  // var args: Array<String>=

     fun startMeasuringMetrics(benchmarkMetric: BenchmarkMetric,baseBenchmark: BaseBenchmark, args: Array<String>){
        var benchmarkResult:BaseBenchmarkResult?=null
        when(benchmarkMetric){
            BenchmarkMetric.EXECUTIONTIME->{
                val startTime=System.currentTimeMillis()
                baseBenchmark.initAlgorithm(args)
                val endTime=System.currentTimeMillis()
                benchmarkResult=ExecutionTimeResult(endTime-startTime)
                convertResultToFile.writeResultToFile(benchmarkResult,"Binary Tree")
            }
            BenchmarkMetric.MEMORY_CONSUMPTION->{
                // get the total memory
                val beforeUsedMem: Long
                val afterUsedMem: Long
                val actualMemUsed: Long
                beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
                baseBenchmark.initAlgorithm(args)
                afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
                actualMemUsed = afterUsedMem - beforeUsedMem
                benchmarkResult=MemoryResult(actualMemUsed,beforeUsedMem,afterUsedMem)
                convertResultToFile.writeResultToFile(benchmarkResult,"Binary Tree")
            }
            BenchmarkMetric.GARBAGE_COLLECTION->{
               // System.gc()
               // var dd= Runtime.getRuntime().gc()
                // Will execute on command
            }
        }
    }

    private fun activateBenchmark(benchmarkType: BenchmarkType, benchmarkImplementation: BenchmarkImplementation){
        when(benchmarkType){
            BenchmarkType.BINARYTREE->{
                when(benchmarkImplementation){
                    BenchmarkImplementation.JAVA->{
                   //   convertResultToFile.writeResultToFile(,"dd")
                    }
                    BenchmarkImplementation.KOTLIN_CONVERTED->{}
                    BenchmarkImplementation.KOTLIN_PURE->{}
                }
            }
            BenchmarkType.FANNKUCH_REDUX->{}
            BenchmarkType.MANDELBROT->{}
            BenchmarkType.REGEX_REDUX->{}
        }
    }

}