package com.wallapop.marsRover.metrics

interface IMetrics {
    fun recordInitializationSuccess()

    fun recordInitializationFailure()

    fun recordCommandSuccess()

    fun recordCommandFailure()
}
