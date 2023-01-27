class TicTacToe2 {
    private val board = Array(3) { CharArray(3) { '-' } }
    private var currentPlayer = 'X'
    private var gameOver = false

    fun play() {
        while (!gameOver) {
            printBoard()
            makeMove()
            checkForWin()
            switchPlayer()
        }
        println("Game over! Player $currentPlayer has won!")
    }

    private fun makeMove() {
        var x: Int
        var y: Int
        do {
            println("Player $currentPlayer, enter x and y coordinates for your move (0-2):")
            x = readLine()!!.toInt()
            y = readLine()!!.toInt()
        } while (!isValidMove(x, y))
        board[x][y] = currentPlayer
    }

    private fun isValidMove(x: Int, y: Int): Boolean {
        return x in 0..2 && y in 0..2 && board[x][y] == '-'
    }

    private fun checkForWin() {
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                gameOver = true
                return
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                gameOver = true
                return
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            gameOver = true
            return
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            gameOver = true
            return
        }
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }

    private fun printBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                print(" ${board[i][j]} ")
            }
            println()
        }
    }
}

fun main() {
    val game = TicTacToe2()
    game.play()
}