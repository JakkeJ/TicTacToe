fun main() {
    val game = TicTacToe()
    game.createBoard()
}

abstract class Game {
    abstract val type: String
    abstract val numberOfPlayers: Int
}

interface GameBoard {
    fun setSize(): Int
    fun isValidMove(): Boolean
}

class Player {

}

class TicTacToe: Game(),GameBoard {
    override val type = "Board Game"
    override val numberOfPlayers = 2

    private val ticTacToeBoard = mutableListOf<MutableList<String>>()
    private val empty = " "

    private val columns = 3
    private val rows = 3
    override fun setSize(): Int {
        return -1
    }

    override fun isValidMove():Boolean{
        return true
    }

    fun createBoard(){
        for (i in 1..rows){
            ticTacToeBoard.add(mutableListOf(empty, empty, empty))}
        println(this.ticTacToeBoard[0])
        println(this.ticTacToeBoard[1])
        println(this.ticTacToeBoard[2])
    }

    fun checkRows(): Int{
        return -1
    }

    fun checkColumns(): Int{
        return -1
    }

    fun checkDiagonals(): Int{
        return -1
    }

    fun printGameBoard(){

    }
}