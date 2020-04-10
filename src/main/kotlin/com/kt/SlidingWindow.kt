package com.kt

import java.util.*

/**
 * i/p [1 2 3 4 5 6 7 8 9 ...]
 * tumbling window of 3
 * waits for 3 elements to arrive
 * [1 2 3]
 * [2 3 4]
 * [4 5 6]
 * ..
 */
class SlidingWindow<T>(private val windowLen: Int, private val aggFn: (List<T>) -> Unit) : WindowFunction<T> {
    private val buffer = Buffer<T>(windowLen)

    override fun processData(t: T) {
        buffer.add(t)
        if (buffer.length == windowLen) {
            aggFn(buffer.getWindow())
        }
    }
}

class Buffer<T>(val length: Int) {
    private val queue: Queue<T> = LinkedList()

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
