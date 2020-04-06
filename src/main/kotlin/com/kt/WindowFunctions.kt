package com.kt

import java.util.*
import kotlinx.coroutines.channels.*

/**
 * i/p [1 2 3 4 5 6 7 8 9 ...]
 * tumbling window of 3
 * waits for 3 elements to arrive
 * [1 2 3]
 * [4 5 6]
 * [6 7 8]
 * ..
 */
class TumblingWindow<T>(private val windowLen: Int, private val aggFn: (List<T>) -> Unit) {
    private var cache = mutableListOf<T>()
    private var state: WindowState = WindowState.INIT

    fun processData(t: T) {
        when (state) {
            WindowState.INIT -> {
                cache.add(t)
                transitState()
            }
            WindowState.ACCEPT -> {
                if (cache.size == windowLen) {
                   aggFn(cache)
                   transitState()
                   cache.clear()
                }
                cache.add(t)
            }
        }
    }

    private fun transitState() {
        state = state.nextState()
    }
}

/**
 * i/p [1 2 3 4 5 6 7 8 9 ...]
 * tumbling window of 3
 * waits for 3 elements to arrive
 * [1 2 3]
 * [2 3 4]
 * [4 5 6]
 * ..
 */
class SlidingWindow<T>(private val windowLen: Int, private val aggFn: (List<T>) -> Unit) {
    private val buffer = Buffer<T>(windowLen)

    fun processData(t: T) {
        buffer.add(t)
        if (buffer.length == windowLen) {
            aggFn(buffer.getWindow())
        }
    }
}

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

enum class WindowState {
    INIT {
        override fun nextState() = ACCEPT
    },

    ACCEPT {
        override fun nextState() = INIT
    };

    abstract fun nextState(): WindowState
}

class Buffer<T>(val length: Int) {
    private val queue: Queue<T> = LinkedList<T>()

    fun add(t: T) {
        if (queue.size == length) {
            queue.poll()
        }
        queue.add(t)
    }

    fun getWindow(): List<T> {
        return queue.toList()
    }

    fun size(): Int {
        return queue.size
    }

    fun clear() {
        queue.clear()
    }
}
