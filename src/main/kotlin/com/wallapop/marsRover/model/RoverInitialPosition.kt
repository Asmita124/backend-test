package com.wallapop.marsRover.model

data class RoverInitialPosition(
    val x: Int,
    val y: Int,
    val direction: String // can be "n", "e", "w", "s"
)
