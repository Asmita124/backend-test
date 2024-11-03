package com.wallapop.marsRover.metrics

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class Metrics(
    private val registry: MeterRegistry
) : IMetrics {

    companion object {
        private const val PREFIX = "marsRover"
        const val INITIALIZATION_SUCCESS = "${PREFIX}_initialization_success"
        const val INITIALIZATION_FAILURE = "${PREFIX}_initialization_failure"
        const val COMMAND_SUCCESS = "${PREFIX}_command_success"
        const val COMMAND_FAILURE = "${PREFIX}_command_failure"
        const val COMMAND_PROCESSING_TIME = "${PREFIX}_command_processing_time"
    }

    override fun recordInitializationSuccess() {
        registry.counter(INITIALIZATION_SUCCESS).increment()
    }

    override fun recordInitializationFailure() {
        registry.counter(INITIALIZATION_FAILURE).increment()
    }

    override fun recordCommandSuccess() {
        registry.counter(COMMAND_SUCCESS).increment()
    }

    override fun recordCommandFailure() {
        registry.counter(COMMAND_FAILURE).increment()
    }

}
