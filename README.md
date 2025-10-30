# Notebook Test Project

This project provides a flexible Tic-Tac-Toe library that handles game logic while allowing developers to create their own UI, and a simple CLI game.

## Project Structure

```
├── lib/                          # Library files for Tic-Tac-Toe game logic
│   ├── Board.kt                  # Board representation and operations
│   ├── Game.kt                   # Game logic
│   ├── Mark.kt                   # Player mark enum (X/O)
│   ├── GameState.kt              # Game state enum (IN_GAME, X_WON, O_WON, DRAW)
│   ├── Player.kt                 # Player data class
│   ├── PlayerType.kt             # Player type enum (HUMAN, COMPUTER_RANDOM, COMPUTER_AI)
│   └── ComputerPlayer.kt         # AI computer opponents
│
├── app/ 
│   ├── main.kt                   # CLI game implementation
```

## How to Play the CLI Game

### Starting the Game

Run the `main.kt` file to start the command-line game:

```bash
kotlinc main.kt -include-runtime -d tictactoe.jar
java -jar tictactoe.jar
```

Or run directly in your Kotlin IDE.

### Game Setup

When you start the game, you'll choose from three game modes:

1. **Human vs Human** - Two players take turns on the same computer
2. **Human vs Computer (Random)** - Play against a computer that makes random moves
3. **Human vs Computer (AI)** - Play against an intelligent AI opponent using minimax algorithm

Enter player names when prompted.

### Playing

- The board is numbered 1-9:
  ```
  1 | 2 | 3
  ---------
  4 | 5 | 6
  ---------
  7 | 8 | 9
  ```
- Player X always goes first
- Enter a number (1-9) to place your mark in that position
- The game ends when a player gets three in a row or the board is full (draw)
- After each game, choose to play again or quit

### Winning

Get three of your marks in a row - horizontally, vertically, or diagonally.

## How to Use the Library

The library provides clean separation between game logic and UI, allowing you to build any interface you want.

Examples are availale at Kotlin Notebook [`tictactoe-libray.ipynb`](./tictactoe-libray.ipynb)
