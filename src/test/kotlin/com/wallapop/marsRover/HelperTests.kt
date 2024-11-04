package com.wallapop.marsRover

import com.wallapop.marsRover.model.Direction
import com.wallapop.marsRover.model.Position
import com.wallapop.marsRover.model.Rover
import com.wallapop.marsRover.service.Helper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelperTests {
    private val helper = Helper()
    private val rover = Rover(position = Position(x = 0, y = 0), direction = Direction.NORTH)
    private val obstacles = listOf(Position(x = 2, y = 359), Position(x = 1, y = 0), Position(x = 1, y =2))

    @Test
    fun `move rover forward`() {
        // Act
        helper.move(rover, 1, obstacles)

        // Assert
        assertEquals(0, rover.position.x)
        assertEquals(1, rover.position.y) // Moved one step north
    }

    @Test
    fun `move rover backward`() {
        // Act
        helper.move(rover, -1, obstacles)

        // Assert
        assertEquals(0, rover.position.x)
        assertEquals(359, rover.position.y) // Moved one step south
    }

    @Test
    fun `move rover forward with wrap around the map boundaries`() {
        rover.position = Position(x = 359, y = 359)
        rover.direction = Direction.EAST

        helper.move(rover, 2, obstacles)

        assertEquals(Position(1, 359), rover.position)
    }
    @Test
    fun `move rover into obstacle`() {
        rover.position = Position(x = 0, y = 0)
        rover.direction = Direction.NORTH

        // Attempt to move into an obstacle
        helper.move(rover, 1, obstacles) // Moving forward to (0, 1) which is not an obstacle

        // Assert that rover moved
        assertEquals(0, rover.position.x)
        assertEquals(1, rover.position.y) // Moved one step north
    }

    @Test
    fun `move rover into obstacle and revert`() {
        rover.position = Position(x = 1, y = 1) // Position is where the obstacle is
        rover.direction = Direction.NORTH

        // Attempt to move forward into the obstacle
        helper.move(rover, 1, obstacles) // Should revert to (1, 1)

        // Assert that rover did not move
        assertEquals(1, rover.position.x)
        assertEquals(1, rover.position.y) // Should still be at obstacle position
    }
    @Test
    fun `rotate rover to the left from north to west`() {
        // Act
        helper.rotateLeft(rover)

        // Assert
        assertEquals(Direction.WEST, rover.direction)
    }

    @Test
    fun `rotate rover to the right from north to east`() {
        // Act
        helper.rotateRight(rover)

        // Assert
        assertEquals(Direction.EAST, rover.direction)
    }

    @Test
    fun `wrapCoordinate wraps correctly for positive overflow`() {
        // Testing a large positive number that should wrap
        val wrappedX = helper.wrapCoordinate(362)
        assertEquals(2, wrappedX)
    }

    @Test
    fun `wrapCoordinate wraps correctly for negative overflow`() {
        // Testing a negative number that should wrap to positive space
        val wrappedX = helper.wrapCoordinate(-2)
        assertEquals(358, wrappedX)
    }

}
