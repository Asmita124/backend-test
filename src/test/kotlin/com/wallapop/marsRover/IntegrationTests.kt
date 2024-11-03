package com.wallapop.marsRover

import com.wallapop.marsRover.model.Direction
import com.wallapop.marsRover.model.RoverInitialPosition
import com.wallapop.marsRover.service.IService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ActiveProfiles("test")
@ExperimentalCoroutinesApi
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class IntegrationTests {

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var service: IService

    @Test
    fun `initialize rover returns 200 on valid input`() = runTest {
        val validPosition = RoverInitialPosition(x = 0, y = 0, Direction.NORTH)

        whenever(service.initializeRover(validPosition)).thenReturn(Unit)

        client.post()
            .uri("/rover/initialize")
            .bodyValue(validPosition)
            .exchange()
            .expectStatus().isOk

        verify(service).initializeRover(validPosition)
    }

    @Test
    fun `move rover returns 204 on valid command`() = runTest {
        val command = "f"

        whenever(service.processCommand(command)).thenReturn(Unit)

        client.post()
            .uri("/rover/move")
            .bodyValue(command)
            .exchange()
            .expectStatus().isNoContent

        verify(service).processCommand(command)
    }

    @Test
    fun `get rover status returns 200 with correct status message`() = runTest {
        val status = "Rover is at x:0 y:0 facing:N"

        whenever(service.getRoverStatus()).thenReturn(status)

        client.get()
            .uri("/rover/status")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>().isEqualTo(status)

        verify(service).getRoverStatus()
    }

}
