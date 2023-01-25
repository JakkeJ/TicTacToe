fun main() {
    val game = TicTacToe()
    game.createBoard()
}

abstract class Game {
    abstract val type: String
    abstract val numberOfPlayers: Int
}

interface GameBoard {
    fun setSize()
}

class Player {

}

class TicTacToe: Game(),GameBoard {
    override val type = "Board Game"
    override val numberOfPlayers = 2

    override fun setSize(){
        return
    }
    
    val empty = " "
    val row1 = mutableListOf<String>(empty, empty, empty)
    val row2 = mutableListOf<String>(empty, empty, empty)
    val row3 = mutableListOf<String>(empty, empty, empty)

    fun createBoard(){
        println(this.row1)
        println(this.row2)
        println(this.row3)
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

    fun isValidMove(){

    }
}