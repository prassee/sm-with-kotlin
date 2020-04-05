/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.kt

import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val channel = Channel<Int>()
    launch {
        for (i in 1..16) channel.send(i)
    }

    WindowFunctions.createTumbling<Int>(3, channel).processWindow { it ->
        println(it)
    }
}