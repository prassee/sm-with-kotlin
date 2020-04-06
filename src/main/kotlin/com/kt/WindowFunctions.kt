package com.kt
import java.util.*
import kotlinx.coroutines.channels.*

/*
utility functions to create windows
*/
object WindowFunctions {
    fun <T> createTumbling(interval: Int, aggFn: (List<T>) -> Unit): TumblingWindow<T> {
        return TumblingWindow<T>(interval, aggFn)
    }

    fun <T> createSliding(interval: Int, aggFn: (List<T>) -> Unit): SlidingWindow<T> {
        return SlidingWindow<T>(interval, aggFn)
    }
}
