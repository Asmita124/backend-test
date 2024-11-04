package com.wallapop.marsRover.service

import com.wallapop.marsRover.model.Position
import com.wallapop.marsRover.model.Rover
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class Helper {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun move(rover: Rover, step: Int, obstacles: List<Position>) {
        // Logic for moving the rover and wrapping around the spherical map
        log.debug(
            "Moving rover. Current position: ({}, {}), Direction: {}",
            rover.position.x,
            rover.position.y,
            rover.direction
        )
        val previousPosition = rover.position.copy()

        // Calculate potential new position
        val newX = wrapCoordinate(rover.position.x + rover.direction.dx * step)
        val newY = wrapCoordinate(rover.position.y + rover.direction.dy * step)
        val newPosition = Position(newX, newY)

        // Check for obstacles
        if (obstacles.contains(newPosition)) {
            log.warn("Obstacle encountered at position: $newPosition. Reverting to previous position: $previousPosition.")
            rover.position = previousPosition // Revert to the previous position
        } else {
            rover.position = newPosition
            log.info("Moved to new position: (${rover.position.x}, ${rover.position.y}) facing ${rover.direction}")
        }

    }

    fun wrapCoordinate(coordinate: Int): Int {
        // Infinite surface simulation: handles both positive and negative overflow by modulo 360
        return (coordinate % 360 + 360) % 360
    }

    fun rotateLeft(rover: Rover) {
        rover.direction = rover.direction.rotateLeft()
    }

    fun rotateRight(rover: Rover) {
        rover.direction = rover.direction.rotateRight()
    }
}