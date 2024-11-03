package com.wallapop.marsRover


import com.wallapop.marsRover.controller.Controller
import com.wallapop.marsRover.model.RoverInitialPosition
import com.wallapop.marsRover.service.IService
import com.wallapop.marsRover.service.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.util.*

@ExperimentalCoroutinesApi
@WebFluxTest(Controller::class)
class ControllerTests {

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var service: Service


    @Test
    fun `initialize rover returns 200`() = runTest {
        // Arrange
        val initialPosition = RoverInitialPosition(x = 0, y = 0, direction = "NORTH")

        whenever(service.initializeRover(initialPosition))
            .thenReturn(Unit)

        // Act & Assert
        client.post()
            .uri("/rover/initialize")
            .bodyValue(initialPosition)
            .exchange()
            .expectStatus().isOk

        // Verify
        verify(service).initializeRover(initialPosition)
    }

    @Test
    fun `move rover command returns 204`() = runTest {
        // Arrange
        val command = "f"  // Example command

        whenever(service.processCommand(command)).thenReturn(Unit)

        // Act & Assert
        client.post()
            .uri("/rover/move")
            .bodyValue(command)
            .exchange()
            .expectStatus().isNoContent

        // Verify
        verify(service).processCommand(command)
    }

    @Test
    fun `get rover status returns 200`() = runTest {
        // Arrange
        val status = "Rover is at x:0 y:0 facing:N"  // Sample status

        whenever(service.getRoverStatus()).thenReturn(status)

        // Act & Assert
        client.get()
            .uri("/rover/status")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo(status)

        // Verify
        verify(service).getRoverStatus()
    }

    @Test
    fun `get rover status returns 500 if rover not initialized`() = runTest {
        // Arrange
        whenever(service.getRoverStatus()).thenThrow(RuntimeException("Rover not initialized"))

        // Act & Assert
        client.get()
            .uri("/rover/status")
            .exchange()
            .expectStatus().is5xxServerError
    }

}