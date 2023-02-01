import java.lang.IndexOutOfBoundsException

fun main() {
    print("Type in the first players name: ")
    val player1 = Player(readln(), 1)
    print("Type in the second players name: ")
    val player2 = Player(readln(),2)
    clearScreen()
    val game = TicTacToe(player1, player2)
    game.startGame()
}

fun clearScreen(){
    for (i in 1..50){ // Very unnecessary but there is no system.os('cls') in Kotlin afaik...
        print("\n")
    }
}

abstract class Game {
    abstract val type: String
    abstract val numberOfPlayers: Int
}

interface GameBoard{
    fun isValidMove(row: Int, column: Int, player: Int): Boolean
    fun createBoard(boardSize: Int)
}

class Player(var name: String, val playerNumber: Int, var score: Int = 0)

class TicTacToe(private var player1: Player, private var player2: Player): Game(),GameBoard {
    init{
        clearScreen()
        println("How to play:")
        println("You use 1-9 to select your square, like the pattern below:")
        println(" 1   2   3")
        println(" 4   5   6")
        println(" 7   8   9")
        println("-Let's go-")
        println("----------")
    }
    private var player1Mark = "X"
    private var player2Mark = "O"
    private var activePlayer = player1
    private var activePlayerMark = player1Mark

    override val type = "Simple Board Game"
    override val numberOfPlayers = 2
    private var ticTacToeBoard = mutableListOf<MutableList<String>>()

    //Lambda expression to check if a value is a number between 0 and 9 or not, returns bool
    private val isANumber = { toCheck: String -> toCheck.toDoubleOrNull() != null }

    private fun validateInput(input: String, isANumber: (String) -> Boolean): Boolean {
        val number: Double? = input.toDoubleOrNull()
        return !isANumber(input) || number == null || number.toInt().toDouble() != number || number !in 1.0..9.0
    }

    override fun isValidMove(row: Int, column: Int, player: Int): Boolean{
        try{
        val checkedCell = ticTacToeBoard[row][column]
        return checkedCell == "-"
        }
        catch (e: IndexOutOfBoundsException){
            return false
        }
    }

    override fun createBoard(boardSize: Int){
        for (i in 0 until boardSize) {
            ticTacToeBoard.add(mutableListOf("-", "-", "-"))
        }
    }
    private fun checkRows(): List<String> {
        for (i in 0..2) {
            if (ticTacToeBoard[i][0] == ticTacToeBoard[i][1] && ticTacToeBoard[i][1] == ticTacToeBoard[i][2] && ticTacToeBoard[i][0] == "X") {
                return listOf(i.toString(),"X")
            }
            if (ticTacToeBoard[i][0] == ticTacToeBoard[i][1] && ticTacToeBoard[i][1] == ticTacToeBoard[i][2] && ticTacToeBoard[i][0] == "O") {
                return listOf(i.toString(),"O")
            }
        }
        return listOf("-1","-")
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
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][2] && ticTacToeBoard[0][0] == "X") { return listOf("1","X") }
        if (ticTacToeBoard[0][2] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][0] && ticTacToeBoard[0][2] == "X") { return listOf("2","X") }
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][2] && ticTacToeBoard[0][0] == "O") { return listOf("1","O") }
        if (ticTacToeBoard[0][2] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][0] && ticTacToeBoard[0][2] == "O") { return listOf("2","O") }
        return listOf("-1","-")
    }

    //Prints the game board
    private fun printGameBoard(){
        println("Tic Tac Toe")
        for (i in 0..2){
            println(" ${ticTacToeBoard[i][0]}   ${ticTacToeBoard[i][1]}   ${ticTacToeBoard[i][2]}")
        }
    }

    //Check who wins
    private fun checkWinner(winList: List<String>, typeOfWin: String){
        if (winList[1] == player1Mark){
            println("Congratulations, ${player1.name}, you won with ${winList[1]} on $typeOfWin ${winList[0].toInt()}")
            player1.score++
            println("The score is now:")
            println("${player1.name} ${player1.score} - ${player2.score} ${player2.name}")
        } else if(winList[1] == player2Mark){
            println("Congratulations, ${player2.name}, you won with ${winList[1]} on $typeOfWin ${winList[0].toInt()}")
            player2.score++
            println("The score is now:")
            println("${player1.name} ${player1.score} - ${player2.score} ${player2.name}")
        }
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
    private fun resetGameBoard(){
        clearScreen()
        println("Swapping who plays first")
        println("${player2.name}, you are now playing first!")
        println("")
        ticTacToeBoard = mutableListOf()
        createBoard(3)
        val tempMark = player1Mark
        player1Mark = player2Mark
        player2Mark = tempMark
        val tempPlayer = player1
        player1 = player2
        player2 = tempPlayer
        activePlayer = player1
        activePlayerMark = player1Mark
    }

    //Turns string input into int (as this has already been checked to be valid input in while loops). Returns index of outer and inner lists in ticTacToeBoard.
    private fun cleanPlayerInput(input: String): List<Int>{
        val cleanInput = input.toDouble().toInt()
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
        for (i in 0 until movesList.size){
            if (movesList[i] == "-"){
                validMovesList.add(i+1)
            }
        }
        return validMovesList
    }

    private fun printValidMoves(currentValidMoves: List<Int>){
        clearScreen()
        printGameBoard()
        println("Valid moves: ")
        for (i in currentValidMoves){
            print("$i ")
        }
        println("")
    }

    //Game loop
    fun startGame(){
        createBoard(3)
        clearScreen()
        while(true){
            printGameBoard()
            val rows = checkRows()
            val columns = checkColumns()
            val diagonals = checkDiagonals()
            val currentValidMoves = validMoves()
            for (i in currentValidMoves){
                print("$i ")
            }

            println("")

            if (rows[0].toInt() != -1){ //If there is a winner on rows, check who it is
                checkWinner(rows,"row")
                if (restartGame() == "y"){
                    resetGameBoard()
                    continue
                } else {
                    break
                }
            }
            if (columns[0].toInt() != -1){ //If there is a winner on columns, check who it is
                checkWinner(columns,"column")
                if (restartGame() == "y"){
                    resetGameBoard()
                    continue
                } else {
                    break
                }
            }
            if (diagonals[0].toInt() != -1){ //If there is a winner on diagonals, check who it is
                checkWinner(diagonals,"diagonal")
                if (restartGame() == "y"){
                    resetGameBoard()
                    continue
                } else {
                    break
                }
            }
            if (currentValidMoves.isEmpty()){
                println("The game is a tie!")
                println("The score is now:")
                println("${player1.name} ${player1.score} - ${player2.score} ${player2.name}")
                if (restartGame() == "y"){
                    resetGameBoard()
                    continue
                } else {
                    break
                }
            }

            println("${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
            var playerInput = readln()

            while (validateInput(playerInput, isANumber)){
                printValidMoves(currentValidMoves)
                println("Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                playerInput = readln()
            }
            var listIndices = cleanPlayerInput(playerInput)
            var isValid = isValidMove(listIndices[0],listIndices[1],activePlayer.playerNumber)

            while (!isValid){
                printValidMoves(currentValidMoves)
                println("Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                playerInput = readln()
                while (validateInput(playerInput, isANumber)){
                    printValidMoves(currentValidMoves)
                    println("Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
                    playerInput = readln()
                }
                listIndices = cleanPlayerInput(playerInput)
                isValid = isValidMove(listIndices[0],listIndices[1],activePlayer.playerNumber)
            }
            clearScreen()
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
}