package com.wallapop.marsRover.config

import com.wallapop.marsRover.model.Position
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "mars")
class ObstacleConfig {
    lateinit var obstacles: List<Position>
}