package com.wallapop.marsRover

import com.wallapop.marsRover.model.Direction
import com.wallapop.marsRover.model.RoverInitialPosition
import com.wallapop.marsRover.service.IService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.openapitools.openapidiff.core.OpenApiCompare
import org.openapitools.openapidiff.core.model.DiffResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.io.File

@ActiveProfiles("test")
@ExperimentalCoroutinesApi
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    @Test
    fun `generate open api`() {
        // Generate OpenApi/Swagger file
        File("./build/backend-test.yaml").writeText(
            client.get().uri("/v3/api-docs.yaml").exchange().expectBody<String>().returnResult().responseBody!!
        )

        // Compare it with current spec
        val diff = OpenApiCompare.fromLocations("./backend-test.yaml", "./build/backend-test.yaml")

        // Fail is there is any diff (i.e. both compatible and incompatible)
        Assertions.assertThat(diff.isChanged).isEqualTo(DiffResult.NO_CHANGES)
    }

}
