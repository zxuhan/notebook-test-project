package org.jetbrains.kotlinx.tictactoe

/**
 * Represents a 3x3 Tic-Tac-Toe board
 * Positions are numbered 1-9:
 * 1 | 2 | 3
 * ---------
 * 4 | 5 | 6
 * ---------
 * 7 | 8 | 9
 */
class Board {
    // Internal board uses 0-indexed array (0-8)
    private val cells = arrayOfNulls<Mark>(9);

    /**
     * Place a mark at the specified position (1-9)
     * Returns true if successful, false if position is invalid or occupied
     */
    fun placeMark(position: Int, mark: Mark): Boolean {
        if (!isValid(position)) {
            return false
        }

        if (getMark(position) != null) {
            return false
        }

        cells[position - 1] = mark
        return true
    }

    /**
     * Get the mark at the specified position (1-9)
     * Returns null if the position is empty
     */
    fun getMark(position: Int): Mark? {
        if (!isValid(position)) {
            return null
        }
        return cells[position - 1]
    }

    /**
     * Check if a position (1-9) is empty/null
     */
    fun isEmpty(position: Int): Boolean {
        if (!isValid(position)) {
            return false
        }
        return getMark(position) == null
    }

    /**
     * Check if a position is valid (1-9)
     */
    fun isValid(position: Int): Boolean {
        return (position - 1) in cells.indices
    }

    /**
     * Clear all marks from the board
     */
    fun clear() {
        for (i in cells.indices) {
            cells[i] = null
        }
    }

    /**
     * Get all cell states (for display purposes)
     * Returns a list of 9 marks (null for empty positions)
     */
    fun getState(): List<Mark?> {
        return cells.toList()
    }

    fun getBoardSize(): Int {
        return cells.size
    }
}