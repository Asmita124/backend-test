package com.wallapop.marsRover.controller

import com.wallapop.marsRover.model.RoverInitialPosition
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import com.wallapop.marsRover.service.IService

@RestController
@Validated
@RequestMapping("/rover")
class Controller(private val service: IService) {

    @PostMapping("/initialize")
    suspend fun initializeRover(
        @RequestBody roverInitialPosition: RoverInitialPosition
    ) = service.initializeRover(roverInitialPosition)

    @PostMapping("/move")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun moveRover(
        @RequestBody command: String
    ) = service.processCommand(command)

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getRoverStatus(): String =
        service.getRoverStatus()

}
