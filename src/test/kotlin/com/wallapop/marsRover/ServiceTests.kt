package com.wallapop.marsRover

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
        service.initializeRover(RoverInitialPosition(x = 0, y = 0, direction = "N"))
    }
    @Test
    fun `initializeRover with invalid direction throws IllegalArgumentException`() = runBlocking {
        val initialPosition = RoverInitialPosition(0, 0, "I")

        assertThrows<IllegalArgumentException> {
            service.initializeRover(initialPosition)
        }
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
        val exception = assertThrows<IllegalArgumentException> {
            service.processCommand("invalid")
        }
        assertEquals("Invalid command", exception.message)
    }

    @Test
    fun `getRoverStatus returns correct position and direction`() = runTest {
        // Assert initial state
        val result = service.getRoverStatus()
        assertEquals("Rover is at x:0 y:0 facing:N", result)
    }
}
