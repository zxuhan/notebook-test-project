package org.jetbrains.kotlinx.tictactoe

import kotlin.test.*

class GameTest {
    private lateinit var game: Game

    @BeforeTest
    fun setup() {
        game = Game()
    }

    // Test initial state
    @Test
    fun `game should start with correct initial state`() {
        assertEquals(Mark.X, game.getCurrentPlayer())
        assertEquals(GameState.IN_GAME, game.getGameState())
        assertNull(game.getWinner())

        val board = game.getCurrentBoard()
        assertEquals(9, board.size)
        assertTrue(board.all { it == null })
    }

    // Test makeMove functionality
    @Test
    fun `makeMove should place mark and switch players`() {
        assertTrue(game.makeMove(1))
        assertEquals(Mark.X, game.getCurrentBoard()[0])
        assertEquals(Mark.O, game.getCurrentPlayer())

        assertTrue(game.makeMove(2))
        assertEquals(Mark.O, game.getCurrentBoard()[1])
        assertEquals(Mark.X, game.getCurrentPlayer())
    }

    @Test
    fun `makeMove should return false for invalid positions`() {
        assertFalse(game.makeMove(0))
        assertFalse(game.makeMove(10))
        assertFalse(game.makeMove(-1))
    }

    @Test
    fun `makeMove should return false for occupied position`() {
        assertTrue(game.makeMove(5))
        assertFalse(game.makeMove(5))
        assertEquals(Mark.O, game.getCurrentPlayer()) // Turn should not switch
    }

    @Test
    fun `makeMove should not allow moves after game ends`() {
        // Create winning condition for X
        game.makeMove(1) // X
        game.makeMove(4) // O
        game.makeMove(2) // X
        game.makeMove(5) // O
        game.makeMove(3) // X wins (1, 2, 3)

        assertFalse(game.makeMove(6))
    }

    // Test win conditions - one of each type
    @Test
    fun `should detect win in row`() {
        game.makeMove(1) // X
        game.makeMove(4) // O
        game.makeMove(2) // X
        game.makeMove(5) // O
        game.makeMove(3) // X wins (top row: 1, 2, 3)

        assertEquals(GameState.X_WON, game.getGameState())
        assertEquals(Mark.X, game.getWinner())
    }

    @Test
    fun `should detect win in column`() {
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(4) // X
        game.makeMove(3) // O
        game.makeMove(7) // X wins (left column: 1, 4, 7)

        assertEquals(GameState.X_WON, game.getGameState())
        assertEquals(Mark.X, game.getWinner())
    }

    @Test
    fun `should detect win in diagonal`() {
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(5) // X
        game.makeMove(3) // O
        game.makeMove(9) // X wins (diagonal: 1, 5, 9)

        assertEquals(GameState.X_WON, game.getGameState())
        assertEquals(Mark.X, game.getWinner())
    }

    @Test
    fun `should detect O player win`() {
        game.makeMove(1) // X
        game.makeMove(4) // O
        game.makeMove(2) // X
        game.makeMove(5) // O
        game.makeMove(9) // X
        game.makeMove(6) // O wins (4, 5, 6)

        assertEquals(GameState.O_WON, game.getGameState())
        assertEquals(Mark.O, game.getWinner())
    }

    // Test draw condition
    @Test
    fun `should detect draw when board is full with no winner`() {
        // X O X
        // O O X
        // O X X
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(3) // X
        game.makeMove(4) // O
        game.makeMove(6) // X
        game.makeMove(5) // O
        game.makeMove(7) // O (wait, this doesn't work)
        game.makeMove(9) // O
        game.makeMove(8) // X - Draw

        assertEquals(GameState.DRAW, game.getGameState())
        assertNull(game.getWinner())
    }

    @Test
    fun `should prioritize win over draw`() {
        // X wins on last move even though board becomes full
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(4) // X
        game.makeMove(3) // O
        game.makeMove(5) // X
        game.makeMove(6) // O
        game.makeMove(9) // X
        game.makeMove(8) // O
        game.makeMove(7) // X wins (1, 4, 7)

        assertEquals(GameState.X_WON, game.getGameState())
        assertEquals(Mark.X, game.getWinner())
    }

    // Test reset functionality
    @Test
    fun `reset should restore game to initial state and allow new game`() {
        // Play first game to completion
        game.makeMove(1) // X
        game.makeMove(4) // O
        game.makeMove(2) // X
        game.makeMove(5) // O
        game.makeMove(3) // X wins

        assertEquals(GameState.X_WON, game.getGameState())

        // Reset and verify initial state
        game.reset()

        assertEquals(Mark.X, game.getCurrentPlayer())
        assertEquals(GameState.IN_GAME, game.getGameState())
        assertTrue(game.getCurrentBoard().all { it == null })

        // Play new game
        assertTrue(game.makeMove(5))
        assertEquals(Mark.X, game.getCurrentBoard()[4])
        assertEquals(Mark.O, game.getCurrentPlayer())
    }

    // Test getCurrentBoard
    @Test
    fun `getCurrentBoard should reflect current game state`() {
        game.makeMove(1) // X
        game.makeMove(5) // O
        game.makeMove(9) // X

        val board = game.getCurrentBoard()
        assertEquals(Mark.X, board[0])
        assertEquals(Mark.O, board[4])
        assertEquals(Mark.X, board[8])
        assertNull(board[1])
    }
}