package com.wallapop.marsRover.service

import com.wallapop.marsRover.model.RoverInitialPosition

interface IService {
    suspend fun initializeRover(roverInitialPosition: RoverInitialPosition)
    suspend fun processCommand(command: String)
    suspend fun getRoverStatus(): String
}
