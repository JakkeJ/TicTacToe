fun main() {
    var game = TicTacToe()
    game.print()
}

abstract class Game {
    abstract val type: String
    abstract val numberOfPlayers: Int
}

class Player {

}

class TicTacToe: Game() {
    override val type = "Board Game"
    override val numberOfPlayers = 2

    fun print(){
        println(type)
    }

    fun checkRows(){

    }

    fun checkColumns(){

    }

    fun checkDiagonals(){

    }

    fun printGameBoard(){

    }

    fun isValidMove(){

    }
}