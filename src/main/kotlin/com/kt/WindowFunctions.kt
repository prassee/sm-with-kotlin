package com.kt

/*
utility functions to create windows
*/
object WindowFunctions {
    fun <T> createTumbling(interval: Int, aggFn: (List<T>) -> Unit): WindowFunction<T> {
        return TumblingWindow<T>(interval, aggFn)
    }

    fun <T> createSliding(interval: Int, aggFn: (List<T>) -> Unit): WindowFunction<T> {
        return SlidingWindow<T>(interval, aggFn)
    }

    fun <T> createWindow(wType: String, interval: Int, aggFn: (List<T>) -> Unit): WindowFunction<T> {
        return when (wType) {
            "sliding" -> createSliding(interval, aggFn)
            "tumbling" -> createTumbling(interval, aggFn)
            else -> createTumbling(interval, aggFn)
        }
    }
}

interface WindowFunction<T> {
    fun processData(t: T)
}