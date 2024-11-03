package com.wallapop.marsRover.model

enum class Direction(val dx: Int, val dy: Int) {
    NORTH(0, 1),  // Moves up in y direction
    EAST(1, 0),   // Moves right in x direction
    SOUTH(0, -1), // Moves down in y direction
    WEST(-1, 0);  // Moves left in x direction

    fun rotateLeft(): Direction = when (this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }

    fun rotateRight(): Direction = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}

data class Position(var x: Int, var y: Int)