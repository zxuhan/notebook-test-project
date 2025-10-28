package org.jetbrains.kotlinx.tictactoe

fun main() {
    println("Welcome to Tic-Tac-Toe!")
    println()

    // Request player names
    print("Enter name for Player X: ")
    val playerXName = readLine() ?: "Player X"

    print("Enter name for Player O: ")
    val playerOName = readLine() ?: "Player O"

    val game = Game()
    var keepPlaying = true

    while (keepPlaying) {

        println()
        println("Let's start the game!")
        println()

        // Reset game for new round
        game.reset()

        // Print initial board
        printBoard(game.getCurrentBoard())

        while (game.getGameState() == GameState.IN_GAME) {
            val currentPlayer = game.getCurrentPlayer()
            val currentPlayerName = if (currentPlayer == Mark.X) playerXName else playerOName

            println("$currentPlayerName ($currentPlayer), enter position (1-9): ")
            val input = readLine()

            // Parse input
            val position = input?.toIntOrNull()

            if (position == null) {
                println("Invalid input! Please enter a number between 1 and 9.")
                println()
                continue
            }

            // Try to make the move
            if (game.makeMove(position)) {
                println()
                printBoard(game.getCurrentBoard())
            } else {
                println("Invalid move! Position must be between 1-9 and not already occupied.")
                println()
            }
        }

        // Print game result
        println()
        when (game.getGameState()) {
            GameState.X_WON -> println("🎉 Congratulations $playerXName! You won!")
            GameState.O_WON -> println("🎉 Congratulations $playerOName! You won!")
            GameState.DRAW -> println("It's a draw! Good game!")
            else -> {} // Should never happen
        }

        // Ask if players want to continue
        println()
        println("What would you like to do?")
        println("1. New game")
        println("2. Quit")
        print("Enter your choice (1 or 2): ")

        when (readLine()?.trim()) {
            "1" -> keepPlaying = true
            "2" -> {
                keepPlaying = false
                println()
                println("Thanks for playing! Goodbye!")
            }

            else -> {
                println("Invalid choice. Exiting game.")
                keepPlaying = false
            }
        }
    }
}


fun printBoard(board: List<Mark?>) {
    println("-------------")
    for (row in 0..2) {
        print("| ")
        for (col in 0..2) {
            val index = row * 3 + col
            val mark = board[index]
            val display = when (mark) {
                Mark.X -> "X"
                Mark.O -> "O"
                null -> (index + 1).toString()
            }
            print("$display | ")
        }
        println()
        println("-------------")
    }
    println()
}