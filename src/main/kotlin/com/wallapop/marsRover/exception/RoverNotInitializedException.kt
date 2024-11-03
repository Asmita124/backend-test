package com.wallapop.marsRover.exception

class RoverNotInitializedException(message: String) : IllegalStateException("No rover has been initialized with the error message $message")
