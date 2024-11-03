/*
package com.adevinta.unichat.realtime.api

import com.wallapop.marsRover.model.Direction
import com.wallapop.marsRover.model.Position
import com.wallapop.marsRover.service.Helper
import com.wallapop.marsRover.service.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ActiveProfiles("test")
@ExperimentalCoroutinesApi
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val webClient: WebTestClient) {

    private val helper = Helper() // Mocking the Helper dependency

    private val service: Service(helper)
    @Test
    fun `initialize rover endpoint sets initial rover position and direction`() = runBlocking {
        val initialPosition = Position(0, 0)
        val initialDirection = Direction.NORTH

        // Act - initialize the rover
        webClient.post()
            .uri("/rover/initialize?x=${initialPosition.x}&y=${initialPosition.y}&direction=${initialDirection}")
            .exchange()
            .expectStatus().isNoContent

        // Assert - verify that the service's rover is set up with correct position and direction
        val roverStatus = service.getRoverStatus()
        assertThat(roverStatus).isEqualTo("Rover is at x:0 y:0 facing:N")
    }

    @Test
    fun `process rover movement command correctly updates position`() = runBlocking {
        val command = "MMRMM"

        // Act - send movement commands
        webClient.post()
            .uri("/rover/processCommand?command=$command")
            .exchange()
            .expectStatus().isNoContent

        // Assert - check final position and direction after movement
        val roverStatus = service.getRoverStatus()
        assertThat(roverStatus).isEqualTo("Rover is at x:2 y:2 facing:E") // Expected result
    }

    @Test
    fun `get rover status endpoint returns current rover status`() = runBlocking {
        // Act - get rover status
        val response = webClient.get()
            .uri("/rover/status")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .returnResult()
            .responseBody

        // Assert - validate status output
        assertThat(response).isEqualTo("Rover is at x:0 y:0 facing:N")
    }

}
*/
