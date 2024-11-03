package com.wallapop.marsRover.controller

import com.wallapop.marsRover.exception.RoverNotInitializedException
import com.wallapop.marsRover.exception.InvalidCommandException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class ExceptionHandler {
  private val log = LoggerFactory.getLogger(this.javaClass)

  @ExceptionHandler(RoverNotInitializedException::class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  fun internalError(e: Error) {
    log.error("Rover not initialized", e)
  }
  @ExceptionHandler(InvalidCommandException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleInvalidCommandException(e: InvalidCommandException): String {
    log.error("Invalid command: ${e.message}", e)
    return "Invalid command: ${e.message}"
  }
}
