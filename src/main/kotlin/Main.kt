fun main() {
    val game = TicTacToe()
    game.createBoard()
    game.startGame(Player("Jim"),Player("Hogne"))
    println(game.checkRows())
    println(game.checkColumns())
    println(game.checkDiagonals())
}

abstract class Game {
    abstract val type: String
    abstract val numberOfPlayers: Int
}

interface GameBoard
{
    fun setSize(): Int
    fun isValidMove(): Boolean
}

class Player(val name: String, val score: Int = 0)

class TicTacToe: Game(),GameBoard {
    override val type = "Board Game"
    override val numberOfPlayers = 2

    private val ticTacToeBoard = mutableListOf<MutableList<String>>()

    //private val rows = 3
    override fun setSize(): Int {
        return 3
    }


    override fun isValidMove():Boolean{
        return true
    }

    fun createBoard()
    {
        for (i in 1..setSize())
        {
            ticTacToeBoard.add(mutableListOf("-", "-", "-"))
        }
    }

    fun checkRows(): Int
    {
        for (i in ticTacToeBoard)
        {
            if (i[0] == i[1] && i[0] == i[2] && i[0] != "-" && i[0] == "X")
            {
                return 0
            }
            if (i[0] == i[1] && i[0] == i[2] && i[0] != "-" && i[0] == "O")
            {
                return 1
            }
        }
        return -1
    }

    fun checkColumns(): Int
    {
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][0] == ticTacToeBoard[2][0] && ticTacToeBoard[0][0] == "X" ||
            ticTacToeBoard[0][1] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][1] && ticTacToeBoard[0][1] == "X" ||
            ticTacToeBoard[0][2] == ticTacToeBoard[1][2] && ticTacToeBoard[1][2] == ticTacToeBoard[2][2] && ticTacToeBoard[0][2] == "X")
        {
            return 0
        }
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][0] == ticTacToeBoard[2][0] && ticTacToeBoard[0][0] == "O" ||
            ticTacToeBoard[0][1] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][1] && ticTacToeBoard[0][1] == "O" ||
            ticTacToeBoard[0][2] == ticTacToeBoard[1][2] && ticTacToeBoard[1][2] == ticTacToeBoard[2][2] && ticTacToeBoard[0][2] == "O")
        {
            return 1
        }
        return -1
    }

    fun checkDiagonals(): Int
    {
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][2] && ticTacToeBoard[0][0] == "X" ||
            ticTacToeBoard[0][2] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][0] && ticTacToeBoard[0][2] == "X")
        {
            return 0
        }
        if (ticTacToeBoard[0][0] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][2] && ticTacToeBoard[0][0] == "O" ||
            ticTacToeBoard[0][2] == ticTacToeBoard[1][1] && ticTacToeBoard[1][1] == ticTacToeBoard[2][0] && ticTacToeBoard[0][2] == "O")
        {
            return 1
        }
        return -1
    }

    fun printGameBoard(){
        println("Tic Tac Toe")
        for (i in 0..2){
            println(" ${ticTacToeBoard[i][0]}   ${ticTacToeBoard[i][1]}   ${ticTacToeBoard[i][2]}")}
    }

    fun startGame(player1: Player, player2: Player){
        println(player1.name)
        while(true){
            printGameBoard()
            break
        }
    }
}