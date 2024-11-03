package com.wallapop.marsRover.exception

class InvalidCommandException(command: String) : RuntimeException("The command given is invalid $command")
