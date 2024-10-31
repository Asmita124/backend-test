fun main() {
    val gridSize = 5 // Assuming a 5x5 grid for the sake of example

    val rovers = listOf(
        Rover(Position(1, 2), Direction.NORTH) to "LMLMLMLMM",
        Rover(Position(3, 3), Direction.EAST) to "MMRMMRMRRM"
    )

    rovers.forEach { (rover, commands) ->
        commands.forEach { command ->
            when (command) {
                'M' -> rover.move(gridSize)
                'L' -> rover.rotateLeft()
                'R' -> rover.rotateRight()
            }
        }
        println(rover.getCurrentState())
    }
}
