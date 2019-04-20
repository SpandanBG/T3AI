package xyz.sudcoding.t3ai

import java.lang.System.out
import java.util.Scanner

object GameHandler {
    private val `in` = Scanner(System.`in`)

    @JvmStatic
    fun main(args: Array<String>) {
        out.println("Welcome to T3AI Tic Tac Toe Game")
        var choice = 1
        do {
            out.print("Would you like to go first(y/n)? : ")
            val playerF = `in`.next()[0]
            out.print("Select your char(o/x)? : ")
            val playerC = `in`.next()[0]

            gameFunc(playerF == 'y', playerC)

            out.print("Play again (1/0)? : ")
            choice = `in`.nextInt()
        } while (choice != 0)
    }

    private fun gameFunc(playerFirst: Boolean, playerChar: Char) {
        var move = IntArray(2)
        try {
            val myGame = T3AI(playerFirst, playerChar)
            while (myGame.playing()) {
                while (myGame.isPlayerTurn) {
                    try {
                        out.print("Player's move: ")
                        move[0] = `in`.nextInt()
                        move[1] = `in`.nextInt()
                        myGame.markMove(move[0], move[1], true)
                        display(myGame.gameState)
                    } catch (e: T3AIException) {
                        if (e.errorTag == 5) {
                            break
                        } else {
                            out.println(e.toString())
                        }
                    }

                }
                while (!myGame.isPlayerTurn) {
                    try {
                        move = myGame.animateCPU()
                        out.println("CPU moved: " + move[0] + ", " + move[1])
                        display(myGame.gameState)
                    } catch (e: T3AIException) {
                        if (e.errorTag == 5) {
                            break
                        } else {
                            out.println(e.toString())
                        }
                    }

                }
            }
            val v = myGame.victor
            out.println(if (v == ' ') "Tie" else "Winner $v")
        } catch (e: T3AIException) {
            out.println(e.toString())
        }

    }

    private fun display(gameBoard: Array<CharArray>) {
        out.println(gameBoard[0][0] + " | " + gameBoard[0][1] + " | " + gameBoard[0][2])
        out.println("-- --- --")
        out.println(gameBoard[1][0] + " | " + gameBoard[1][1] + " | " + gameBoard[1][2])
        out.println("-- --- --")
        out.println(gameBoard[2][0] + " | " + gameBoard[2][1] + " | " + gameBoard[2][2])
        out.println()
    }
}