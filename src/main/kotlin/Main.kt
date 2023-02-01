fun main() {
    print("Type in the first players name: ")
    val player1 = Player(readln(), 1)
    print("Type in the second players name: ")
    val player2 = Player(readln(),2)
    val game = TicTacToe(player1, player2)



    game.startGame()
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
        println("How to play:")
        println("You use 1-9 to select your square, like the pattern below:")
        println(" 1   2   3")
        println(" 4   5   6")
        println(" 7   8   9")
        println("-Let's go-")
        println("----------")
    }
        override val type = "Simple Board Game"
        override val numberOfPlayers = 2
        var ticTacToeBoard = mutableListOf<MutableList<String>>()

    //Lambda expression to check if a value is a number or not, returns bool
    val isANumber = { toCheck: String -> toCheck.toDoubleOrNull() != null }

    override fun isValidMove(row: Int, column: Int, player: Int): Boolean{
        val checkedCell = ticTacToeBoard[row][column]
        return checkedCell == "-"
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

    //Method to print the game board
    private fun printGameBoard(){
        println("Tic Tac Toe")
        for (i in 0..2){
            println(" ${ticTacToeBoard[i][0]}   ${ticTacToeBoard[i][1]}   ${ticTacToeBoard[i][2]}")
        }
    }

    //Method to check who wins
    private fun checkWhoWins(winList: List<String>, player1Name: Player, player2Name: Player, typeOfWin: String){
        val player1Mark = "X"
        val player2Mark = "O"
        if (winList[1] == player1Mark){
            println("Congratulations, ${player1Name.name}, you won with ${winList[1]} on $typeOfWin ${winList[0].toInt()}")
            player1Name.score++
            println("The score is now:")
            println("${player1Name.name} ${player1Name.score} - ${player2Name.score} ${player2Name.name}")
        } else if(winList[1] == player2Mark){
            println("Congratulations, ${player2Name.name}, you won with ${winList[1]} on $typeOfWin ${winList[0].toInt()}")
            player2Name.score++
            println("The score is now:")
            println("${player1Name.name} ${player1Name.score} - ${player2Name.score} ${player2Name.name}")
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

    //Turns string input into int (as this has already been checked to be valid input in while loops). Returns index of outer and inner lists in ticTacToeBoard.
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
        for (i in 0 until movesList.size){
            if (movesList[i] == "-"){
                validMovesList.add(i+1)
            }
        }
        return validMovesList
    }

    //Game loop
    fun startGame(){
        createBoard(3)
        val player1Mark = "X"
        val player2Mark = "O"
        var activePlayer = player1
        var activePlayerMark = player1Mark

        while(true){
            printGameBoard()
            val rows = checkRows()
            if (rows[0].toInt() != -1){ //If there is a winner on rows, check who it is
                checkWhoWins(rows,player1,player2,"row")
                if (restartGame() == "y"){
                    ticTacToeBoard = mutableListOf()
                    createBoard(3)
                    activePlayer = player1
                    activePlayerMark = player1Mark
                } else {
                    break
                }
            }

            val columns = checkColumns()
            if (columns[0].toInt() != -1){ //If there is a winner on columns, check who it is
                checkWhoWins(columns,player1,player2,"column")
                if (restartGame() == "y"){
                    ticTacToeBoard = mutableListOf()
                    createBoard(3)
                    activePlayer = player1
                    activePlayerMark = player1Mark
                } else {
                    break
                }
            }

            val diagonals = checkDiagonals()
            if (diagonals[0].toInt() != -1){ //If there is a winner on diagonals, check who it is
                checkWhoWins(diagonals,player1,player2,"diagonal")
                if (restartGame() == "y"){
                    ticTacToeBoard = mutableListOf()
                    createBoard(3)
                    activePlayer = player1
                    activePlayerMark = player1Mark
                } else {
                    break
                }
            }
            val currentValidMoves = validMoves()
            if (currentValidMoves.isEmpty()){
                println("The game is a tie!")
                if (restartGame() == "y"){
                    ticTacToeBoard = mutableListOf()
                    createBoard(3)
                    activePlayer = player1
                    activePlayerMark = player1Mark
                } else {
                    break
                }
            }
            println("Valid moves: ")
            for (i in currentValidMoves){
                print("$i ")
            }
            println("")
            println("${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:")
            var playerInput = readln()
            while (playerInput == "0" || !isANumber(playerInput)){
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
                while (playerInput == "0" || !isANumber(playerInput)){
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
}