package com.kt

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
class TumblingWindow<T>(private val windowLen: Int, private val stream: Channel<T>) {
    private var internalState = mutableListOf<T>()
    
    suspend fun processWindow(items: (List<T>) -> Unit) {
        stream.consumeEach {
            if (internalState.size == windowLen) {
                items(internalState)
                clearState()
            }
            internalState.add(it)
         }
    }

    fun clearState() {
        internalState.clear()
    }
}

/*
utility functions to create windows
*/
object WindowFunctions {

    fun <T> createTumbling(interval: Int, stream: Channel<T>): TumblingWindow<T> {
        return TumblingWindow<T>(interval, stream)
    }
}
}
