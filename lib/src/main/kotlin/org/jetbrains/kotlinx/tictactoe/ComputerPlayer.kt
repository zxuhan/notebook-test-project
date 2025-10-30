package org.jetbrains.kotlinx.tictactoe


/**
 * Handles computer player moves with different strategies
 */
object ComputerPlayer {

    /**
     * Make a random move on available positions
     * Keeps trying until it finds a valid empty position
     */
    fun makeRandomMove(game: Game) {
        val emptyPositions = getEmptyPositions(game)

        game.makeMove(emptyPositions.random())
    }

    fun makeAIMove(game: Game) {
        val emptyPositions = getEmptyPositions(game)

        // AI plays 5 if he plays first
        if (emptyPositions.size == 9) {
            game.makeMove(5)
            return
        }

        val computerMark = game.getCurrentPlayer()

        var bestScore = Int.MIN_VALUE
        var bestMove = emptyPositions[0]

        for (position in emptyPositions) {
            val gameCopy = game.copy()
            gameCopy.makeMove(position)

            val score = miniMax(gameCopy, 0, false, computerMark)

            if (score >= bestScore) {
                bestScore = score
                bestMove = position
            }
        }

        game.makeMove(bestMove)
    }

    /**
     * Minimax algorithm to find the best move
     * @param game The game state to evaluate
     * @param depth Current depth in the game tree
     * @param isMaximizing Whether this is a maximizing or minimizing player
     * @param computerMark The mark of the computer player (maximizing player)
     * @return The score for this game state
     */
    private fun miniMax(game: Game, depth: Int, isMaximizing: Boolean, computerMark: Mark): Int {
        val gameState = game.getGameState()

        when (gameState) {
            GameState.X_WON -> return if (computerMark == Mark.X) 10 - depth else depth - 10
            GameState.O_WON -> return if (computerMark == Mark.O) 10 - depth else depth - 10
            GameState.DRAW ->  return 0
            GameState.IN_GAME -> {}
        }

        val emptyPositions = getEmptyPositions(game)

        if (isMaximizing) {
            var maxScore = Int.MIN_VALUE
            for (position in emptyPositions) {
                val gameCopy = game.copy()
                gameCopy.makeMove(position)
                val score = miniMax(gameCopy, depth + 1, false, computerMark)
                maxScore = maxOf(maxScore, score)
            }
            return maxScore
        } else {
            var minScore = Int.MAX_VALUE
            for (position in emptyPositions) {
                val gameCopy = game.copy()
                gameCopy.makeMove(position)
                val score = miniMax(gameCopy, depth + 1, true, computerMark)
                minScore = minOf(minScore, score)
            }
            return minScore
        }
    }

    private fun getEmptyPositions(game: Game): MutableList<Int> {
        val boardState = game.getCurrentBoard()
        val emptyPositions = mutableListOf<Int>()
        for (position in 1..9) {
            if (boardState[position - 1] == null) {
                emptyPositions.add(position)
            }
        }
        return emptyPositions
    }
}