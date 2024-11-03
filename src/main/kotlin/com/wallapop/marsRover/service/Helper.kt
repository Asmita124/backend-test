package com.wallapop.marsRover.service

import com.wallapop.marsRover.model.Rover
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class Helper {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun move(rover: Rover, step: Int) {
        // Logic for moving the rover and wrapping around the spherical map
        log.debug("Moving rover. Current position: ({}, {}), Direction: {}", rover.position.x, rover.position.y, rover.direction)
        val previousPosition = rover.position.copy()

        rover.position.x = wrapCoordinate(rover.position.x + rover.direction.dx * step)
        rover.position.y = wrapCoordinate(rover.position.y + rover.direction.dy * step)
        log.info("Moved from ($previousPosition) to (${rover.position}) facing ${rover.direction}")
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