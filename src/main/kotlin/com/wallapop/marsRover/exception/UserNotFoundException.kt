package com.adevinta.unichat.realtime.api.exception

class RoverNotInitializedException(message: String) : IllegalStateException("No rover has been initialized with the error message $message")
