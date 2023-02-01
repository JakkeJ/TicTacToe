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
        val checkedCell = ticTacToeBoard[row][column]
        return checkedCell == "-"
    }
    private fun createBoard(boardSize: Int) {
        val size = setSize(boardSize)
        for (i in 1..size) {
            ticTacToeBoard.add(mutableListOf("-", "-", "-"))
        }
    }
    private fun checkRows(): List<String> {
        for (i in 0..2) {
            if (ticTacToeBoard[i][0] == ticTacToeBoard[i][1] && ticTacToeBoard[i][1] == ticTacToeBoard[i][2] && ticTacToeBoard[i][0] == "X") {
                return mutableListOf(i.toString(),"X")
            }
            if (ticTacToeBoard[i][0] == ticTacToeBoard[i][1] && ticTacToeBoard[i][1] == ticTacToeBoard[i][2] && ticTacToeBoard[i][0] == "O") {
                return mutableListOf(i.toString(),"O")
            }
        }
        return mutableListOf("-1","-")
    }

    private fun checkColumns(): List<String> {
        for (i in 0..2) {
            if (ticTacToeBoard[0][i] == ticTacToeBoard[1][i] && ticTacToeBoard[1][i] == ticTacToeBoard[2][i] && ticTacToeBoard[0][i] != "-") {
                return listOf(i.toString(), ticTacToeBoard[0][i])
            }
        }
        return listOf("-1", "-")
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
                if (rows[1] == player1Mark){
                    println("Congratulations, ${player1.name}, you won with ${rows[1]} on rows ${rows[0].toInt()}")
                    player1.score += 1
                } else {
                    println("Congratulations, ${player2.name}, you won with ${rows[1]} on rows ${rows[0].toInt()}")
                    player2.score += 1
                }
                if (restartGame() == "y"){
                    createBoard(3)
                    activePlayer = player1
                    activePlayerMark = player1Mark
                } else {
                    break
                }
            }
            if (columns[0].toInt() != -1){
                if (columns[1] == player1Mark){
                    println("Congratulations, ${player1.name}, you won with ${columns[1]} on column ${columns[0].toInt()}")
                    player1.score += 1
                } else {
                    println("Congratulations, ${player2.name}, you won with ${columns[1]} on column ${columns[0].toInt()}")
                    player2.score += 1
                }
                if (restartGame() == "y"){
                    createBoard(3)
                    activePlayer = player1
                    activePlayerMark = player1Mark
                } else {
                    break
                }
            }
            if (diagonals[0].toInt() != -1){
                if (diagonals[1] == player1Mark){
                    println("Congratulations, ${player1.name}, you won with ${diagonals[1]} on diagonal ${diagonals[0].toInt()}")
                    player1.score += 1
                } else {
                    println("Congratulations, ${player2.name}, you won with ${diagonals[1]} on diagonal ${diagonals[0].toInt()}")
                    player2.score += 1
                }
                if (restartGame() == "y"){
                    createBoard(3)
                    activePlayer = player1
                    activePlayerMark = player1Mark
                } else {
                    break
                }
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
            var listIndices = cleanPlayerInput(playerInput)
            var isValid = isValidMove(listIndices[0],listIndices[1],activePlayer.playerNumber)
            while (!isValid){
                println("${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                playerInput = readln()
                listIndices = cleanPlayerInput(playerInput)
                isValid = isValidMove(listIndices[0],listIndices[1],activePlayer.playerNumber)
                while (!isNumeric(playerInput)){
                    println("Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                    playerInput = readln()
                }
            }
            if (activePlayer == player1){
                ticTacToeBoard[listIndices[0]][listIndices[1]] = player1Mark
                activePlayerMark = player2Mark
                activePlayer = player2
                continue
            }
                ticTacToeBoard[listIndices[0]][listIndices[1]] = player2Mark
                activePlayerMark = player1Mark
                activePlayer = player1
        }
    }
    //Check if a value is numeric or not with toDoubleOrNull
    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.toDoubleOrNull() != null
    }
    //Restarts the game if input is Y or y
    private fun restartGame(): String{
        while (true) {
            print("Enter Y to start a new game, N to quit: ")
            val input = readln().lowercase()
            if (input == "y" || input == "n") {
                if(input == "n"){ println("Quitting") }
                return input
            } else {
                println("Invalid input, please try again.")
            }
        }
    }
    //Turns string input into int (as this has already been checked to be valid in while loops). Returns index of outer and inner lists in ticTacToeBoard.
    private fun cleanPlayerInput(input: String): List<Int>{
        val cleanInput = input.toInt()
        val outerIndex = (cleanInput-1) / 3
        val innerIndex = (cleanInput-1) % 3
        return mutableListOf(outerIndex,innerIndex)
    }

    //Print all moves that can be done, used to print valid moves and check if a move is valid
    private fun validMoves(): List<Int> {
        val movesList = mutableListOf<String>()
        val validMovesList = mutableListOf<Int>()
        for (i in ticTacToeBoard) {
            for (j in i){
                    movesList.add(j)
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