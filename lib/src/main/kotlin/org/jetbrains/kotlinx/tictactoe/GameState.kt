package org.jetbrains.kotlinx.tictactoe

/**
 * Represents the current state of a Tic-Tac-Toe game
 */
enum class GameState {
    /** Game is still in progress */
    IN_GAME,

    /** Player X has won the game */
    X_WON,

    /** Player O has won the game */
    O_WON,

    /** Game ended in a draw (board is full and no winner) */
    DRAW
}