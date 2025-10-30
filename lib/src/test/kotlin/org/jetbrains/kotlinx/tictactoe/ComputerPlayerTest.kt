package org.jetbrains.kotlinx.tictactoe

import kotlin.test.*

class ComputerPlayerTest {
    private lateinit var game: Game

    @BeforeTest
    fun setup() {
        game = Game()
    }

    // ========== RANDOM PLAYER TESTS ==========

    @Test
    fun `makeRandomMove should place mark on empty position`() {
        ComputerPlayer.makeRandomMove(game)

        val board = game.getCurrentBoard()
        val marksPlaced = board.count { it != null }

        assertEquals(1, marksPlaced)
        assertEquals(Mark.X, board.find { it != null })
    }

    @Test
    fun `makeRandomMove should work on nearly full board`() {
        // Board state:
        // X | O | X
        // ---------
        // O | O | X
        // ---------
        // X | 8 | 9
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(3) // X
        game.makeMove(4) // O
        game.makeMove(6) // X
        game.makeMove(5) // O
        game.makeMove(7) // X

        // Positions 8 and 9 are empty, O's turn
        ComputerPlayer.makeRandomMove(game)

        val board = game.getCurrentBoard()
        val position8Filled = board[7] == Mark.O
        val position9Filled = board[8] == Mark.O

        // O should have played in position 8 or 9
        assertTrue(position8Filled || position9Filled)
    }

    // ========== AI PLAYER TESTS - CATEGORY 1: IMMEDIATE WIN ==========

    @Test
    fun `AI should take immediate winning move in row`() {
        // Board state (X's turn):
        // X | X | 3
        // ---------
        // O | O | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(1) // X
        game.makeMove(4) // O
        game.makeMove(2) // X
        game.makeMove(5) // O

        // AI (X) can win immediately by playing position 3
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.X, game.getCurrentBoard()[2])
        assertEquals(GameState.X_WON, game.getGameState())
    }

    @Test
    fun `AI should take immediate winning move in column`() {
        // Board state (X's turn):
        // X | O | 3
        // ---------
        // X | O | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(4) // X
        game.makeMove(5) // O

        // AI (X) can win immediately by playing position 7 (completes column 1-4-7)
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.X, game.getCurrentBoard()[6])
        assertEquals(GameState.X_WON, game.getGameState())
    }

    @Test
    fun `AI should take immediate winning move in diagonal`() {
        // Board state (X's turn):
        // X | O | 3
        // ---------
        // O | X | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(5) // X
        game.makeMove(4) // O

        // AI (X) can win immediately by playing position 9
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.X, game.getCurrentBoard()[8])
        assertEquals(GameState.X_WON, game.getGameState())
    }

    // ========== AI PLAYER TESTS - CATEGORY 2: BLOCK OPPONENT WIN ==========

    @Test
    fun `AI should block opponent's immediate win threat in row`() {
        // Board state (O's turn):
        // X | X | 3
        // ---------
        // O | 5 | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(1) // X
        game.makeMove(4) // O
        game.makeMove(2) // X

        // X threatens to win at position 3 (row 1-2-3)
        // AI (O) must block by playing position 3
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.O, game.getCurrentBoard()[2])
    }

    @Test
    fun `AI should block opponent's immediate win threat in column`() {
        // Board state (X's turn):
        // X | O | X
        // ---------
        // 4 | O | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(1) // X
        game.makeMove(2) // O
        game.makeMove(3) // X
        game.makeMove(5) // O

        // O threatens to win at position 8 (column 2-5-8)
        // AI (X) must block by playing position 8
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.X, game.getCurrentBoard()[7])
    }

    @Test
    fun `AI should block opponent's immediate win threat in diagonal`() {
        // Board state (O's turn):
        // X | 2 | O
        // ---------
        // 4 | X | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(1) // X
        game.makeMove(3) // O
        game.makeMove(5) // X

        // X threatens to win at position 9 (diagonal 1-5-9)
        // AI (O) must block by playing position 9
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.O, game.getCurrentBoard()[8])
    }

    // ========== AI PLAYER TESTS - CATEGORY 3: WIN > BLOCK ==========

    @Test
    fun `AI should prioritize winning over blocking`() {
        // Board state (X's turn):
        // X | X | 3
        // ---------
        // O | O | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(1) // X
        game.makeMove(4) // O
        game.makeMove(2) // X
        game.makeMove(5) // O

        // AI (X) can:
        // - Win immediately at position 3 (row 1-2-3)
        // - Block O at position 6 (row 4-5-6)
        // Minimax chooses WIN (score 10) over BLOCK (score 0)
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.X, game.getCurrentBoard()[2])
        assertEquals(GameState.X_WON, game.getGameState())
    }

    // ========== AI PLAYER TESTS - OPTIMAL OPENING STRATEGY ==========

    @Test
    fun `AI should take center on empty board`() {
        // Board state (X's turn):
        // 1 | 2 | 3
        // ---------
        // 4 | 5 | 6
        // ---------
        // 7 | 8 | 9

        // Center is the optimal opening move
        // Minimax evaluates all positions and center gives best outcome
        ComputerPlayer.makeAIMove(game)

        assertEquals(Mark.X, game.getCurrentBoard()[4])
    }

    @Test
    fun `AI should take corner when opponent has center`() {
        // Board state (O's turn):
        // 1 | 2 | 3
        // ---------
        // 4 | X | 6
        // ---------
        // 7 | 8 | 9
        game.makeMove(5) // X takes center

        // Best response to center is corner
        // Minimax shows corners give best defensive positions
        ComputerPlayer.makeAIMove(game)

        val board = game.getCurrentBoard()
        val corner1 = board[0] == Mark.O
        val corner3 = board[2] == Mark.O
        val corner7 = board[6] == Mark.O
        val corner9 = board[8] == Mark.O

        val tookCorner = corner1 || corner3 || corner7 || corner9
        assertTrue(tookCorner)
    }
}