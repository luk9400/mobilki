package com.example.tictac

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_main.*

enum class Turn { O, X }

class MainActivity : AppCompatActivity() {

    var turn = Turn.O

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        table()
        turnText.text = turn.toString() + "'s turn"
    }

    fun table() {
        for (i in 0..4) {
            table.setColumnShrinkable(i, true)
        }
    }

    fun turn(button: Button, turn: Turn): Turn {
        button.text = turn.toString()
        if (turn == Turn.O) {
            return Turn.X
        } else {
            return Turn.O
        }
    }

    fun click(view: View) {
        if ((view as Button).text == "") {
            if (hasWon() == null) {
                turn = turn(view, turn)
                turnText.text = turn.toString() + "'s turn"
                if (hasWon() != null) {
                    turnText.text = hasWon().toString() + " won!!!"
                }
            }
        }
    }

    fun hasWon(): Turn? {
        if (checkWin(Turn.O)) {
            return Turn.O
        }
        if (checkWin(Turn.X)) {
            return Turn.X
        }
        return null
    }

    fun checkWin(turn: Turn): Boolean {
        //vertical line
        for (i in 0 .. table.childCount - 1) {
            val row: TableRow = table.getChildAt(i) as TableRow
            if ((row.getChildAt(0) as Button).text == turn.toString() && (row.getChildAt(1) as Button).text == turn.toString() && (row.getChildAt(2) as Button).text == turn.toString() && (row.getChildAt(3) as Button).text == turn.toString() && (row.getChildAt(4) as Button).text == turn.toString()) {
                return true
            }
        }

        //horizontal line
        val column0: TableRow = table.getChildAt(0) as TableRow
        val column1: TableRow = table.getChildAt(1) as TableRow
        val column2: TableRow = table.getChildAt(2) as TableRow
        val column3: TableRow = table.getChildAt(3) as TableRow
        val column4: TableRow = table.getChildAt(4) as TableRow

        for (i in 0 .. column1.childCount - 1) {
            if ((column0.getChildAt(i) as Button).text == turn.toString() && (column1.getChildAt(i) as Button).text == turn.toString() && (column2.getChildAt(i) as Button).text == turn.toString() && (column3.getChildAt(i) as Button).text == turn.toString() && (column4.getChildAt(i) as Button).text == turn.toString()) {
                return true
            }
        }

        //diagonals
        if ((column0.getChildAt(0) as Button).text == turn.toString() && (column1.getChildAt(1) as Button).text == turn.toString() && (column2.getChildAt(2) as Button).text == turn.toString() && (column3.getChildAt(3) as Button).text == turn.toString() && (column4.getChildAt(4) as Button).text == turn.toString()) {
            return true
        }
        if ((column0.getChildAt(4) as Button).text == turn.toString() && (column1.getChildAt(3) as Button).text == turn.toString() && (column2.getChildAt(2) as Button).text == turn.toString() && (column3.getChildAt(1) as Button).text == turn.toString() && (column4.getChildAt(0) as Button).text == turn.toString()) {
            return true
        }

        return false
    }
}
