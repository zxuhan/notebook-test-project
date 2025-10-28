package org.jetbrains.kotlinx.tictactoe

import kotlin.test.*

class BoardTest {
    private lateinit var board: Board

    @BeforeTest
    fun setup() {
        board = Board()
    }

    // Test placeMark functionality
    @Test
    fun `placeMark should place mark at valid position`() {
        assertTrue(board.placeMark(1, Mark.X))
        assertEquals(Mark.X, board.getMark(1))
    }

    @Test
    fun `placeMark should return false for invalid positions`() {
        assertFalse(board.placeMark(0, Mark.X))
        assertFalse(board.placeMark(10, Mark.X))
        assertFalse(board.placeMark(-1, Mark.X))
    }

    @Test
    fun `placeMark should return false for already occupied position`() {
        board.placeMark(5, Mark.X)
        assertFalse(board.placeMark(5, Mark.O))
        assertEquals(Mark.X, board.getMark(5))
    }

    @Test
    fun `placeMark should work for all valid positions`() {
        for (position in 1..9) {
            val mark = if (position % 2 == 0) Mark.O else Mark.X
            assertTrue(board.placeMark(position, mark))
            assertEquals(mark, board.getMark(position))
        }
    }

    // Test getMark functionality
    @Test
    fun `getMark should return null for empty position`() {
        assertNull(board.getMark(5))
    }

    @Test
    fun `getMark should return null for invalid positions`() {
        assertNull(board.getMark(0))
        assertNull(board.getMark(10))
    }

    @Test
    fun `getMark should return correct mark after placement`() {
        board.placeMark(3, Mark.O)
        assertEquals(Mark.O, board.getMark(3))
    }

    // Test isEmpty functionality
    @Test
    fun `isEmpty should return true for empty and false for occupied positions`() {
        assertTrue(board.isEmpty(1))

        board.placeMark(7, Mark.X)
        assertFalse(board.isEmpty(7))
    }

    @Test
    fun `isEmpty should return false for invalid positions`() {
        assertFalse(board.isEmpty(0))
        assertFalse(board.isEmpty(10))
    }

    // Test isValid functionality
    @Test
    fun `isValid should return true for positions 1 to 9`() {
        for (position in 1..9) {
            assertTrue(board.isValid(position))
        }
    }

    @Test
    fun `isValid should return false for out of range positions`() {
        assertFalse(board.isValid(0))
        assertFalse(board.isValid(10))
        assertFalse(board.isValid(-5))
    }

    // Test clear functionality
    @Test
    fun `clear should remove all marks and allow new placements`() {
        board.placeMark(1, Mark.X)
        board.placeMark(5, Mark.O)
        board.placeMark(9, Mark.X)

        board.clear()

        for (position in 1..9) {
            assertNull(board.getMark(position))
        }

        assertTrue(board.placeMark(5, Mark.O))
        assertEquals(Mark.O, board.getMark(5))
    }

    // Test getState functionality
    @Test
    fun `getState should return list of 9 nulls for empty board`() {
        val state = board.getState()
        assertEquals(9, state.size)
        assertTrue(state.all { it == null })
    }

    @Test
    fun `getState should return correct board state`() {
        board.placeMark(1, Mark.X)
        board.placeMark(5, Mark.O)
        board.placeMark(9, Mark.X)

        val state = board.getState()
        assertEquals(Mark.X, state[0])
        assertEquals(Mark.O, state[4])
        assertEquals(Mark.X, state[8])
        assertNull(state[1])
        assertNull(state[2])
    }

    @Test
    fun `getState should return immutable copy of board state`() {
        board.placeMark(1, Mark.X)
        val state1 = board.getState()
        board.placeMark(2, Mark.O)
        val state2 = board.getState()

        // State1 should not be affected by changes to board
        assertEquals(Mark.X, state1[0])
        assertNull(state1[1])

        assertEquals(Mark.X, state2[0])
        assertEquals(Mark.O, state2[1])
    }

    // Test getBoardSize
    @Test
    fun `getBoardSize should return 9`() {
        assertEquals(9, board.getBoardSize())
    }
}