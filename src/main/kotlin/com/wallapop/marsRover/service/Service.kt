package com.wallapop.marsRover.service

import com.wallapop.marsRover.config.ObstacleConfig
import com.wallapop.marsRover.exception.InvalidCommandException
import com.wallapop.marsRover.exception.RoverNotInitializedException
import com.wallapop.marsRover.metrics.IMetrics
import com.wallapop.marsRover.model.Position
import com.wallapop.marsRover.model.Rover
import com.wallapop.marsRover.model.RoverInitialPosition
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.system.measureTimeMillis

@Service
class Service(
    private val helper: Helper,
    private val obstacleConfig: ObstacleConfig,
    private val metrics: IMetrics
) : IService {
    private val log = LoggerFactory.getLogger(this::class.java)
    private var rover: Rover? = null

    override suspend fun initializeRover(roverInitialPosition: RoverInitialPosition) {
        try {
            val direction = roverInitialPosition.direction
            rover = Rover(Position(roverInitialPosition.x, roverInitialPosition.y), direction)
            log.info("Initialized rover at position: $roverInitialPosition")
            metrics.recordInitializationSuccess()
        } catch (e: Exception) {
            metrics.recordInitializationFailure()
            throw RoverNotInitializedException("Failed to initialize rover")
        }
    }


    override suspend fun processCommand(command: String) {
        // Check for initialization
        val currentRover = getInitializedRover()

        // Retrieve the predefined obstacles from the obstacleConfig
        val obstacles = obstacleConfig.obstacles

        if (!listOf("f", "b", "l", "r").contains(command)) {
            metrics.recordCommandFailure()
            throw InvalidCommandException(command)
        }
        when (command) {
            "f" -> helper.move(currentRover, 1, obstacles)
            "b" -> helper.move(currentRover, -1, obstacles)
            "l" -> {
                helper.rotateLeft(currentRover)
            }

            "r" -> {
                helper.rotateRight(currentRover)
            }

            else -> throw InvalidCommandException("Invalid command")
        }

        // Logging the current position of the rover
        log.info("Current position of the rover is: ${currentRover.position}")

        metrics.recordCommandSuccess()
    }

    override suspend fun getRoverStatus(): String {
        val currentRover = getInitializedRover()
        return "Rover is at x:${currentRover.position.x} y:${currentRover.position.y} facing:${currentRover.direction.name[0]}"
    }

    fun getInitializedRover(): Rover {
        return rover ?: throw RoverNotInitializedException("Rover has not been initialized.")
    }


    // THIS IS ADDITIONAL FEATURE THAT COULD BE IMPLEMENTED WHERE THERE ARE MULTIPLE ROVERS
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

}