package org.jetbrains.kotlinx.tictactoe

import kotlin.random.Random

/**
 * Handles computer player moves with different strategies
 */
object ComputerPlayer {

    /**
     * Make a random move on available positions
     * Keeps trying until it finds a valid empty position
     */
    fun makeRandomMove(boardState: List<Mark?>): Int {
        val emptyPositions = mutableListOf<Int>()
        for (position in 1..9) {
            if (boardState[position - 1] == null) {
                emptyPositions.add(position)
            }
        }

        if (emptyPositions.isEmpty()) {
            throw IllegalStateException("No empty positions available")
        }

        return emptyPositions.random()
    }
}