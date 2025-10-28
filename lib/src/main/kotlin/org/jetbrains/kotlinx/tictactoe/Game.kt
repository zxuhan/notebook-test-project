package org.jetbrains.kotlinx.tictactoe

/**
 * Manages a game of Tic-Tac-Toe
 */
class Game {
    private val board = Board()
    private var currentPlayer = Mark.X
    private var gameState = GameState.IN_GAME

    private val winningCombinations = listOf(
        listOf(1, 2, 3),  // Top row
        listOf(4, 5, 6),  // Middle row
        listOf(7, 8, 9),  // Bottom row
        listOf(1, 4, 7),  // Left column
        listOf(2, 5, 8),  // Middle column
        listOf(3, 6, 9),  // Right column
        listOf(1, 5, 9),  // Diagonal top-left to bottom-right
        listOf(3, 5, 7)   // Diagonal top-right to bottom-left
    )

    /**
     * Attempt to make a move at the specified position for the current player
     *
     * @param position The position to place a mark (1-9)
     * @return true if the move was successful, false if the position is invalid,
     *         occupied, or the game is already over
     */
    fun makeMove(position: Int): Boolean{
        if (gameState != GameState.IN_GAME){
            return false
        }

        if (!board.placeMark(position, currentPlayer)) {
            return false
        }

        if (checkWinner(currentPlayer)) {
            gameState = if (currentPlayer == Mark.X) GameState.X_WON else GameState.O_WON
            return true
        }

        if (isBoardFull()) {
            gameState = GameState.DRAW
            return true
        }

        currentPlayer = if (currentPlayer == Mark.X) Mark.O else Mark.X
        return true
    }

    /**
     * Check if the specified mark has achieved a winning combination
     *
     * @param mark The mark to check for a win
     * @return true if the mark has won, false otherwise
     */
    private fun checkWinner(mark: Mark): Boolean {
        for (combination in winningCombinations){
            if (combination.all { position -> board.getMark(position) == mark }) {
                return true
            }
        }
        return false
    }

    /**
     * Check if the board is completely filled
     *
     * @return true if all positions are occupied, false otherwise
     */
    private fun isBoardFull(): Boolean {
        return board.getState().all { mark -> mark != null }
    }

    /**
     * Get the mark of the player whose turn it is
     *
     * @return The current player's mark (X or O)
     */
    fun getCurrentPlayer(): Mark {
        return currentPlayer
    }

    /**
     * Get the current state of the game
     *
     * @return The current game state (IN_GAME, X_WON, O_WON, or DRAW)
     */
    fun getGameState(): GameState {
        return gameState
    }

    /**
     * Get the winner of the game, if any
     *
     * @return The winning mark (X or O), or null if the game is ongoing or ended in a draw
     */
    fun getWinner(): Mark? {
        return when (gameState) {
            GameState.X_WON -> Mark.X
            GameState.O_WON -> Mark.O
            else -> null
        }
    }

    /**
     * Get the current state of the board
     *
     * @return A list of 9 marks representing the board state (null for empty positions)
     */
    fun getCurrentBoard(): List<Mark?> {
        return board.getState()
    }

    /**
     * Reset the game to its initial state
     *
     * Clears the board, sets the current player to X, and resets the game state to IN_GAME
     */
    fun reset() {
        board.clear()
        currentPlayer = Mark.X
        gameState = GameState.IN_GAME
    }
}