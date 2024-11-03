package com.wallapop.marsRover

import com.wallapop.marsRover.exception.InvalidCommandException
import com.wallapop.marsRover.exception.RoverNotInitializedException
import com.wallapop.marsRover.model.Direction
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
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class ServiceTests {

    private val helper = Helper() // Mocking the Helper dependency
    private val service: Service = Service(helper)

    @BeforeEach
    fun initializeRover() = runBlocking {
        // Initialize the rover at a default position before each test
        service.initializeRover(RoverInitialPosition(x = 0, y = 0, Direction.NORTH))
    }

    @Test
    fun `processCommand moves rover forward`() = runBlocking {
        val command = "f"
        // Act
        service.processCommand(command)

        // Assert
        val status = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:1 facing:N", status)
    }
    @Test
    fun `processCommand moves rover backward`() = runBlocking {
        val command = "b"
        // Act
        service.processCommand(command)

        // Assert
        val status = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:359 facing:N", status)
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
    }

    @Test
    fun `getRoverStatus returns correct position and direction`() = runTest {
        // Assert initial state
        val result = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:0 facing:N", result)
    }
}
