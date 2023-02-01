import java.lang.NumberFormatException

fun main() {
    val game = TicTacToe()
    println("Type in the first players name: ")
    val player1 = Player(readln(), 1)
    println("Type in the second players name: ")
    val player2 = Player(readln(),2)
    game.startGame(player1,player2)
}

abstract class Game {
    abstract val type: String
    abstract val numberOfPlayers: Int
}

interface GameBoard
{
    fun isValidMove(row: Int, column: Int, player: Int): Boolean
}

class Player(var name: String, val playerNumber: Int, var score: Int = 0)


class TicTacToe(): Game(),GameBoard {
    override val type = "Board Game"
    override val numberOfPlayers = 2
    private val ticTacToeBoard = mutableListOf<MutableList<String>>()

    //Lambda expression to set the size of the board. This is not very useful but all other methods are way too big to rewrite as lambdas.
    val setSize: (Int) -> Int = { size -> size }

    override fun isValidMove(row: Int, column: Int, player: Int): Boolean{
        var listOfCells = mutableListOf<String>()
        for (i in ticTacToeBoard){
            for (j in i){
                listOfCells.add(j)
            }
        }
        val validMovesList = validMoves()
        println(listOfCells)
        println(validMovesList)
        for (i in 0 until listOfCells.size){
            if (validMovesList[i] == )
        }
        return false
    }

    private fun createBoard(boardSize: Int) {
        val size = setSize(boardSize)
        for (i in 1..size) {
            ticTacToeBoard.add(mutableListOf("-", "-", "-"))
        }
    }

    private fun checkRows(): List<String> {
        if (ticTacToeBoard[0][0] == ticTacToeBoard[0][1] && ticTacToeBoard[0][1] == ticTacToeBoard[0][2] && ticTacToeBoard[0][0] == "X") {
            return mutableListOf("0","X")
        }
        if (ticTacToeBoard[1][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[1][2] && ticTacToeBoard[1][0] == "X") {
            return mutableListOf("1","X")
        }
        if (ticTacToeBoard[2][0] == ticTacToeBoard[2][1] && ticTacToeBoard[2][1] == ticTacToeBoard[2][2] && ticTacToeBoard[2][0] == "X") {
            return mutableListOf("2","X")
        }
        if (ticTacToeBoard[0][0] == ticTacToeBoard[0][1] && ticTacToeBoard[0][1] == ticTacToeBoard[0][2] && ticTacToeBoard[0][0] == "O") {
            return mutableListOf("0","O")
        }
        if (ticTacToeBoard[1][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[1][2] && ticTacToeBoard[1][0] == "O") {
            return mutableListOf("1","O")
        }
        if (ticTacToeBoard[2][0] == ticTacToeBoard[2][1] && ticTacToeBoard[2][1] == ticTacToeBoard[2][2] && ticTacToeBoard[2][0] == "O") {
            return mutableListOf("2","O")
        }
        return mutableListOf("-1","-")
    }

    private fun checkColumns(): List<String> {
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][0] && ticTacToeBoard[1][0] == ticTacToeBoard[2][0] && ticTacToeBoard[0][0] == "X") {
            return mutableListOf("0","X")
        }
        if (ticTacToeBoard[0][1] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][1] && ticTacToeBoard[0][1] == "X") {
            return mutableListOf("1","X")
        }
        if (ticTacToeBoard[0][2] == ticTacToeBoard[1][2] && ticTacToeBoard[1][2] == ticTacToeBoard[2][2] && ticTacToeBoard[0][2] == "X") {
            return mutableListOf("2","X")
        }
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][0] && ticTacToeBoard[1][0] == ticTacToeBoard[2][0] && ticTacToeBoard[0][0] == "O") {
            return mutableListOf("0","O")
        }
        if (ticTacToeBoard[0][1] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][1] && ticTacToeBoard[0][1] == "O") {
            return mutableListOf("1","O")
        }
        if (ticTacToeBoard[0][2] == ticTacToeBoard[1][2] && ticTacToeBoard[1][2] == ticTacToeBoard[2][2] && ticTacToeBoard[0][2] == "O") {
            return mutableListOf("2","O")
        }
        return mutableListOf("-1","-")
    }

    private fun checkDiagonals(): List<String> {
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][2] && ticTacToeBoard[0][0] == "X") {
            return mutableListOf("1","X")
        }
        if (ticTacToeBoard[0][2] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][0] && ticTacToeBoard[0][2] == "X") {
            return mutableListOf("2","X")
        }
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][2] && ticTacToeBoard[0][0] == "O") {
            return mutableListOf("1","O")
        }
        if (ticTacToeBoard[0][2] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][0] && ticTacToeBoard[0][2] == "O") {
            return mutableListOf("2","O")
        }
        return mutableListOf("-1","-")
    }

    //Lambda expression to print the game board
    val printGameBoard = {
        println("Tic Tac Toe")
        for (i in 0..2) {
            println(" ${ticTacToeBoard[i][0]}   ${ticTacToeBoard[i][1]}   ${ticTacToeBoard[i][2]}")
        }
    }

    fun startGame(player1: Player, player2: Player){
        createBoard(3)
        var winner:Int = 0
        val player1Mark = "X"
        val player2Mark = "O"
        var activePlayer = player1
        var activePlayerMark = player1Mark
        while(true){
            printGameBoard()
            val rows = checkRows()
            val columns = checkColumns()
            val diagonals = checkDiagonals()
            if (rows[0].toInt() != -1){
                println("${rows[1]} won on row ${rows[0].toInt()}")
                if (rows[1] == player1Mark){
                    println("Congratulations, ${player1.name}")
                } else {
                    println("Congratulations ${player2.name}")
                }
                break
            }
            if (columns[0].toInt() != -1){
                println("${columns[1]} won on row ${columns[0].toInt()}")
                if (columns[1] == player1Mark){
                    println("Congratulations, ${player1.name}")
                } else {
                    println("Congratulations ${player2.name}")
                }
                break
            }
            if (diagonals[0].toInt() != -1){
                println("${diagonals[1]} won on diagonal ${diagonals[0].toInt()}")
                if (diagonals[1] == player1Mark){
                    println("Congratulations, ${player1.name}")
                } else {
                    println("Congratulations ${player2.name}")
                }
                break
            }
            val currentValidMoves = validMoves()
            println("Valid moves: ")
            for (i in currentValidMoves){
                print("$i ")
            }
            println("")
            println("${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
            var playerInput = readln()
            while (!isNumeric(playerInput)){
                println("Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                playerInput = readln()
            }
            val cleanInput = playerInput.toInt()
            val outerIndex = (cleanInput-1) / 3
            val innerIndex = (cleanInput-1) % 3
            val isValid = isValidMove(outerIndex,innerIndex,activePlayer.playerNumber)
            while (!isValid){
                println("${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                playerInput = readln()
                while (!isNumeric(playerInput)){
                    println("Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                    playerInput = readln()
                }
            }
            if (activePlayer == player1){
                ticTacToeBoard[outerIndex][innerIndex] = player1Mark
                activePlayerMark = player2Mark
                activePlayer = player2
                continue
            }
            if (activePlayer == player2){
                ticTacToeBoard[outerIndex][innerIndex] = player2Mark
                activePlayerMark = player1Mark
                activePlayer = player1
                continue
            }
        }
    }

    //Check if a value is numeric or not with toDoubleOrNull
    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.toDoubleOrNull() != null
    }

    //Print all moves that can be done, used to print valid moves and check if a move is valid
    private fun validMoves(): List<Int> {
        var movesList = mutableListOf<String>()
        var validMovesList = mutableListOf<Int>()
        for (i in ticTacToeBoard) {
            for (j in i){
                    movesList.add("$j")
                }
            }
        val listSize = movesList.size-1
        for (i in 0..listSize){
            if (movesList[i] == "-"){
                validMovesList.add(i+1)
            }
        }
        return validMovesList
    }
}