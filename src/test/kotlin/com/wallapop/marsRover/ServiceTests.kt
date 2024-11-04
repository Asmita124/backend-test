package com.wallapop.marsRover

import com.wallapop.marsRover.config.ObstacleConfig
import com.wallapop.marsRover.metrics.IMetrics
import com.wallapop.marsRover.exception.InvalidCommandException
import com.wallapop.marsRover.model.Direction
import com.wallapop.marsRover.model.Position
import com.wallapop.marsRover.model.Rover
import com.wallapop.marsRover.service.Helper
import com.wallapop.marsRover.model.RoverInitialPosition
import com.wallapop.marsRover.service.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class ServiceTests {

    private val obstacles = listOf(
        Position(x = 2, y = 359),
        Position(x = 1, y = 0),
        Position(x = 1, y = 359),
        Position(x = 1, y = 1),
        Position(x = 0, y = 1)
    )

    private val helper = Helper() // Mocking the Helper dependency
    private val metrics: IMetrics = mock()
    private val obstacleConfig: ObstacleConfig = mock {
        on { obstacles } doReturn obstacles // Return the predefined obstacles
    }
    private val service: Service = Service(helper, obstacleConfig, metrics)

    private val rover = Rover(Position(x = 0, y = 0), Direction.NORTH)

    @BeforeEach
    fun initializeRover() = runBlocking {
        // Initialize the rover at a default position before each test
        service.initializeRover(RoverInitialPosition(x = 0, y = 0, Direction.NORTH))
    }


    @Test
    fun `processCommand moves rover forward`() = runBlocking {
        val command = "f"
        service.initializeRover(RoverInitialPosition(x = 3, y = 1, Direction.NORTH))
        // Act
        service.processCommand(command)

        // Assert
        val status = service.getRoverStatus()
        assertEquals("Rover is at x:3 y:2 facing:N", status)

        verify(metrics).recordCommandSuccess()
        verify(metrics, never()).recordCommandFailure()
    }

    @Test
    fun `processCommand moves rover backward`() = runBlocking {
        val command = "b"
        // Act
        service.processCommand(command)

        // Assert
        val status = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:359 facing:N", status)

        verify(metrics).recordCommandSuccess()
        verify(metrics, never()).recordCommandFailure()
    }

    @Test
    fun `processCommand moves rover into an obstacle and reverts position`() = runBlocking {
        // Setup: Simulating an obstacle at position (0, 1)
        val command = "f"

        // Act
        service.processCommand(command)

        // Assert
        val status = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:0 facing:N", status) // Should not have moved

        verify(metrics, never()).recordCommandFailure()
        verify(metrics).recordCommandSuccess()
    }

    @Test
    fun `processCommand rotates rover left`() = runTest {
        // Act
        service.processCommand("l")

        // Assert that the rover rotated left
        val status = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:0 facing:W", status)
    }

    @Test
    fun `processCommand throws error on invalid command`() = runTest {
        val command = "I"
        val exception = assertThrows<InvalidCommandException> {
            service.processCommand(command)
        }
        assertEquals("The command given is invalid $command", exception.message)

        verify(metrics).recordCommandFailure()
        verify(metrics, never()).recordCommandSuccess()
    }

    @Test
    fun `getRoverStatus returns correct position and direction`() = runTest {
        // Assert initial state
        val result = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:0 facing:N", result)
    }
}
