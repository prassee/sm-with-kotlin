package com.kt

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

enum class WindowState {
    INIT {
        override fun nextState() = ACCEPT
    },

    ACCEPT {
        override fun nextState() = INIT
    };

    abstract fun nextState(): WindowState
}
