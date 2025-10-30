package org.jetbrains.kotlinx.tictactoe

fun main() {
    println("Welcome to Tic-Tac-Toe!")
    println()

    val game = Game()
    var keepPlaying = true

    while (keepPlaying) {

        // Setup players for this round
        println("Game Setup")
        println("==========")
        println("1. Play against another human")
        println("2. Play against computer (Random moves)")
        println("3. Play against computer (AI opponent)")
        print("Choose game mode (1-3): ")

        val gameMode = readLine()?.trim()

        val playerX: Player
        val playerO: Player

        when (gameMode) {
            "1" -> {
                // Human vs Human
                print("Enter name for Player X: ")
                val playerXName = readLine() ?: "Player X"

                print("Enter name for Player O: ")
                val playerOName = readLine() ?: "Player O"

                playerX = Player(playerXName, Mark.X, PlayerType.HUMAN)
                playerO = Player(playerOName, Mark.O, PlayerType.HUMAN)
            }
            "2" -> {
                // Human vs Computer (Random)
                print("Enter your name for Player X: ")
                val humanName = readLine() ?: "Player X"

                playerX = Player(humanName, Mark.X, PlayerType.HUMAN)
                playerO = Player("Computer (Random)", Mark.O, PlayerType.COMPUTER_RANDOM)
            }
            "3" -> {
                // Human vs Computer (AI)
                print("Enter your name for Player X: ")
                val humanName = readLine() ?: "Player X"

                playerX = Player(humanName, Mark.X, PlayerType.HUMAN)
                playerO = Player("Computer (AI)", Mark.O, PlayerType.COMPUTER_AI)
            }
            else -> {
                println("Invalid choice. Starting Human vs Human game.")
                print("Enter name for Player X: ")
                val playerXName = readLine() ?: "Player X"

                print("Enter name for Player O: ")
                val playerOName = readLine() ?: "Player O"

                playerX = Player(playerXName, Mark.X, PlayerType.HUMAN)
                playerO = Player(playerOName, Mark.O, PlayerType.HUMAN)
            }
        }

        println()
        println("Let's start the game!")
        println("${playerX.name} (X) vs ${playerO.name} (O)")
        println()

        // Reset game for new round
        game.reset()

        // Print initial board
        printBoard(game.getCurrentBoard())

        while (game.getGameState() == GameState.IN_GAME) {
            val currentPlayer = if (game.getCurrentPlayer() == Mark.X) playerX else playerO

            if (currentPlayer.type == PlayerType.HUMAN) {
                // Human player turn
                println("${currentPlayer.name} (${currentPlayer.mark}), enter position (1-9): ")
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
            } else {
                // Computer player turn
                println("${currentPlayer.name} is thinking...")

                when (currentPlayer.type) {
                    PlayerType.COMPUTER_RANDOM -> {
                        Thread.sleep(500) // Small delay for better UX
                        ComputerPlayer.makeRandomMove(game)
                    }
                    PlayerType.COMPUTER_AI -> {
                        Thread.sleep(500) // Small delay for better UX
                        ComputerPlayer.makeAIMove(game)
                    }
                    else -> throw IllegalStateException("Unexpected player type")
                }

                // The computer already made the move, just display it
                println("${currentPlayer.name} plays! Now your turn!")
                println()

                printBoard(game.getCurrentBoard())
            }

        }

        // Print game result
        println()
        when (game.getGameState()) {
            GameState.X_WON -> println("🎉 Congratulations ${playerX.name} (X)! You won!")
            GameState.O_WON -> println("🎉 Congratulations ${playerO.name} (O)! You won!")
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

        if (keepPlaying) {
            println()
            println("=".repeat(50))
            println()
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