class Rover(private var position: Position, var direction: Direction) {
    fun move(gridSize: Int) {
        when (direction) {
            Direction.NORTH -> position.y = (position.y + 1) % gridSize
            Direction.SOUTH -> position.y = (position.y - 1 + gridSize) % gridSize
            Direction.EAST -> position.x = (position.x + 1) % gridSize
            Direction.WEST -> position.x = (position.x - 1 + gridSize) % gridSize
        }
    }

    fun rotateLeft() {
        direction = direction.rotateLeft()
    }

    fun rotateRight() {
        direction = direction.rotateRight()
    }

    fun getCurrentState(): String {
        return "Rover is at x:${position.x} y:${position.y} facing:${direction.name[0]}"
    }
}
