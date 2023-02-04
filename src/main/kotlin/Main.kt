const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val MAGENTA = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val WHITE = "\u001B[37m"
const val RESET = "\u001b[0m"

const val X_COLOR = YELLOW
const val O_COLOR = MAGENTA

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
        println(BLUE + "How to play:")
        println("You use 1-9 to select your square, like the pattern below:")
        println(" 1   2   3")
        println(" 4   5   6")
        println(" 7   8   9")
        println("-Let's go-")
        println("----------$RESET")
    }
    private var player1Mark = "X"
    private var player2Mark = "O"
    private var activePlayer = player1
    private var activePlayerMark = player1Mark

    override val type = "Simple Board Game"
    override val numberOfPlayers = 2
    private var ticTacToeBoard = mutableListOf<MutableList<String>>()
    private var ticTacToeBoardPrint = mutableListOf<MutableList<String>>()

    //Lambda expression to check if a value is a number between 0 and 9 or not, returns bool
    private val isANumber = { toCheck: String -> toCheck.toDoubleOrNull() != null }

    private fun validateInput(input: String, isANumber: (String) -> Boolean): Boolean {
        val number: Double? = input.toDoubleOrNull()
        return !isANumber(input) || number == null || number.toInt().toDouble() != number || number !in 1.0..9.0
    }

    override fun isValidMove(row: Int, column: Int, player: Int): Boolean{
        return try{
            val checkedCell = ticTacToeBoard[row][column]
            checkedCell == "-"
        } catch (e: IndexOutOfBoundsException){
            false
        }
    }

    override fun createBoard(boardSize: Int){
        for (i in 0 until boardSize) {
            ticTacToeBoard.add(mutableListOf("-", "-", "-"))
            ticTacToeBoardPrint.add(mutableListOf("-", "-", "-"))
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
            if (ticTacToeBoard[i][0] == "X"){
                ticTacToeBoardPrint[i][0] = "${X_COLOR}X$RESET"
            } else if (ticTacToeBoard[i][0] == "O"){
                ticTacToeBoardPrint[i][0] = "${O_COLOR}O$RESET"
            }
            if (ticTacToeBoard[i][1] == "X"){
                ticTacToeBoardPrint[i][1] = "${X_COLOR}X$RESET"
            } else if (ticTacToeBoard[i][1] == "O"){
                ticTacToeBoardPrint[i][1] = "${O_COLOR}O$RESET"
            }
            if (ticTacToeBoard[i][2] == "X"){
                ticTacToeBoardPrint[i][2] = "${X_COLOR}X$RESET"
            } else if (ticTacToeBoard[i][2] == "O"){
                ticTacToeBoardPrint[i][2] = "${O_COLOR}O$RESET"
            }
            println(" ${ticTacToeBoardPrint[i][0]}   ${ticTacToeBoardPrint[i][1]}   ${ticTacToeBoardPrint[i][2]}")
        }
    }

    //Check who wins
    private fun checkWinner(winList: List<String>, typeOfWin: String){
        if (winList[1] == player1Mark){
            println("${GREEN}Congratulations, ${player1.name}, you won with ${winList[1]} on $typeOfWin ${winList[0].toInt()}$RESET")
            player1.score++
            println("The score is now:")
        } else if(winList[1] == player2Mark){
            println("${GREEN}Congratulations, ${player2.name}, you won with ${winList[1]} on $typeOfWin ${winList[0].toInt()}$RESET")
            player2.score++
            println("The score is now:")
        }
        println("${GREEN}${player1.name} ${player1.score} - ${player2.score} ${player2.name} $RESET")
    }

    //Restarts the game if input is Y or y
    private fun restartGame(): String{
        while (true) {
            print("Enter$GREEN Y$RESET to start a new game,$RED N$RESET to quit: ")
            val input = readln().lowercase()
            if (input == "y" || input == "n") {
                if(input == "n"){ println("$MAGENTA Quitting $RESET") }
                return input
            } else {
                println("$RED Invalid input, please try again.$RESET")
            }
        }
    }
    //Resets the game board
    private fun resetGameBoard(){
        clearScreen()
        println("${BLUE}Swapping who plays first$RESET")
        println("$MAGENTA${player2.name}, you are now playing first!$RESET")
        println("")
        ticTacToeBoard = mutableListOf()
        ticTacToeBoardPrint = mutableListOf()
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
        while(true){
            printGameBoard()
            val rows = checkRows()
            val columns = checkColumns()
            val diagonals = checkDiagonals()
            val currentValidMoves = validMoves()
            for (i in currentValidMoves){
                print("$CYAN$i$RESET, ")
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
                println("$YELLOW ${player1.name} ${player1.score} - ${player2.score} ${player2.name}$RESET")
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
                println("$RED Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:$RESET")
                playerInput = readln()
            }
            var listIndices = cleanPlayerInput(playerInput)
            var isValid = isValidMove(listIndices[0],listIndices[1],activePlayer.playerNumber)

            while (!isValid){
                printValidMoves(currentValidMoves)
                println("$RED Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:$RESET")
                playerInput = readln()
                while (validateInput(playerInput, isANumber)){
                    printValidMoves(currentValidMoves)
                    println("$RED Not a valid move! ${activePlayer.name}, you are ${activePlayerMark}, enter a valid move:$RESET")
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

/*
Klasse Player - variablene lagrer navn, spillernummer, poeng

Abstrakt klasse "Game" - variablene har "type spill" og "antall spillere"
interface "GameBoard" - funksjonene isValidMove og createBoard

Klasse TicTacToe - Arver abstrakt klasse "Game" og interface "GameBoard", har init-funksjon som printer "How to play"

Lambda-funksjon: isANumber - input parameter "toCheck" (en String) -> bruker todoubleornull, returnerer en boolean

Høyere ordens funksjon: validateInput - sjekker at det er 1-9, ikke float/double eller null - husket hvordan det funket i forrige uke...


Metoder i TicTacToe (utenom lambda og HO funksjon):

isValidMove - returnerer boolean, sjekker om rute har "-" eller ikke

createBoard - override - lager brettet i for-løkke 0 til (men ikke med) boardSize

checkRows, checkColumns, checkDiagonals - sjekker om noen har vunnet, returnerer liste = [rad/kolonne/diagonal der spiller vant, og spillermerke]

printGameBoard - skriver ut spillebrettet

checkWinner - Sjekker om X eller O vant, og printer ut score

restartGame - spør bruker om å starte på nytt eller avslutte - tar kun imot "y" eller "n"

resetGameBoard - Bytter hvem som spiller først, sletter alt som er i listene slik at de er tomme.

cleanPlayerInput - gjør slik at 1-9 blir til rad og kolonne siden vi MÅTTE ha dette i isValid

validMoves - returnerer en liste over gyldige trekk i formatet 1-9 slik input er i spillet.

printValidMoves - skriver ut gyldige trekk.

startGame - Gameloop, tre if sjekker etter vinner og kjører metodene restartGame() og potensielt resetGameBoard().
		Etter disse kommer en sjekk på uavgjort. Så kommer tre while som sjekker om input er rett


klasser X
arv X
abstrakt klasse (tips: Game) X
liste eller array
init-funksjoner X
interface X
lamdafunksjoner (lage egne) og høyere ordens funksjoner X X
 */