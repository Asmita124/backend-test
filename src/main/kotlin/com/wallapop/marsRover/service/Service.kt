package com.wallapop.marsRover.service

import com.adevinta.unichat.realtime.api.exception.RoverNotInitializedException
import com.wallapop.marsRover.model.Direction
import com.wallapop.marsRover.model.Position
import com.wallapop.marsRover.model.Rover
import com.wallapop.marsRover.model.RoverInitialPosition
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class Service(
    private val helper: Helper
) : IService {
    private val log = LoggerFactory.getLogger(this::class.java)
    private var rover: Rover? = null

    override suspend fun initializeRover(roverInitialPosition: RoverInitialPosition) {
        val direction = when (roverInitialPosition.direction.lowercase()) {
            "n" -> Direction.NORTH
            "e" -> Direction.EAST
            "w" -> Direction.WEST
            "s" -> Direction.SOUTH
            else -> throw IllegalArgumentException("Invalid direction")
        }
        rover = Rover(Position(roverInitialPosition.x, roverInitialPosition.y), direction)
        log.info("Initialized rover at position: $roverInitialPosition")
    }

    override suspend fun processCommand(command: String) {
        // Check for initialization
        val currentRover = getInitializedRover()
        when (command) {
            "f" -> helper.move(currentRover, 1)
            "b" -> helper.move(currentRover, -1)
            "l" -> helper.rotateLeft(currentRover)
            "r" -> helper.rotateRight(currentRover)
            else -> throw IllegalArgumentException("Invalid command")
        }
    }

    /*suspend fun processRovers(gridSize: Int, rovers: List<Pair<Rover, String>>) {
        rovers.forEach { (rover, commands) ->
            commands.forEach { command ->
                when (command) {
                    'M' -> helper.move(rover, 1)
                    'L' -> helper.rotateLeft(rover)
                    'R' -> helper.rotateRight(rover)
                }
            }
            println(rover.getCurrentState())
        }
    }*/

    override suspend fun getRoverStatus(): String {
        val currentRover = getInitializedRover()
        return "Rover is at x:${currentRover.position.x} y:${currentRover.position.y} facing:${currentRover.direction.name[0]}"
    }

    fun getInitializedRover(): Rover {
        return rover ?: throw RoverNotInitializedException("Rover has not been initialized.")
    }

}