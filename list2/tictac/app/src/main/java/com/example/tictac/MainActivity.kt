package com.example.tictac

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

enum class Turn { O, X }

class MainActivity : AppCompatActivity() {

    var turn = Turn.O
    var bot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        table()
        switchBot.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            switchBot(switchBot)
        }
        turnText.text = turn.toString() + "'s turn"
    }

    fun table() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        for (i in 0 until table.childCount) {
            val row = table.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                row.getChildAt(j).layoutParams.height = width / (table.childCount + 1)
                row.getChildAt(j).layoutParams.width = width / (table.childCount + 1)
                //row.getChildAt(j).setBackgroundColor(android.graphics.Color.GRAY)
            }
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

    fun switchBot(view: View) {
        bot = (view as Switch).isChecked
    }

    fun click(view: View) {
        if ((view as Button).text == "") {
            switchBot.isClickable = false
            if (hasWon() == null) {
                turn = turn(view, turn)
                turnText.text = turn.toString() + "'s turn"
                if (hasWon() != null) {
                    turnText.text = hasWon().toString() + " won!!!"
                }
            }
            if (hasWon() == null && bot) {
                var button: Button
                while (true) {
                    button = (table.getChildAt(Random.nextInt(5)) as TableRow).getChildAt(Random.nextInt(5)) as Button
                    if (button.text == "") {
                        break
                    }
                }
                turn = turn(button, turn)
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
        for (i in 0 until table.childCount) {
            val row: TableRow = table.getChildAt(i) as TableRow
            if ((row.getChildAt(0) as Button).text == turn.toString() && (row.getChildAt(1) as Button).text == turn.toString() && (row.getChildAt(
                    2
                ) as Button).text == turn.toString() && (row.getChildAt(3) as Button).text == turn.toString() && (row.getChildAt(
                    4
                ) as Button).text == turn.toString()
            ) {
                return true
            }
        }

        //horizontal line
        val column0: TableRow = table.getChildAt(0) as TableRow
        val column1: TableRow = table.getChildAt(1) as TableRow
        val column2: TableRow = table.getChildAt(2) as TableRow
        val column3: TableRow = table.getChildAt(3) as TableRow
        val column4: TableRow = table.getChildAt(4) as TableRow

        for (i in 0 until column1.childCount) {
            if ((column0.getChildAt(i) as Button).text == turn.toString() && (column1.getChildAt(i) as Button).text == turn.toString() && (column2.getChildAt(
                    i
                ) as Button).text == turn.toString() && (column3.getChildAt(i) as Button).text == turn.toString() && (column4.getChildAt(
                    i
                ) as Button).text == turn.toString()
            ) {
                return true
            }
        }

        //diagonals
        if ((column0.getChildAt(0) as Button).text == turn.toString() && (column1.getChildAt(1) as Button).text == turn.toString() && (column2.getChildAt(
                2
            ) as Button).text == turn.toString() && (column3.getChildAt(3) as Button).text == turn.toString() && (column4.getChildAt(
                4
            ) as Button).text == turn.toString()
        ) {
            return true
        }
        if ((column0.getChildAt(4) as Button).text == turn.toString() && (column1.getChildAt(3) as Button).text == turn.toString() && (column2.getChildAt(
                2
            ) as Button).text == turn.toString() && (column3.getChildAt(1) as Button).text == turn.toString() && (column4.getChildAt(
                0
            ) as Button).text == turn.toString()
        ) {
            return true
        }

        return false
    }
}
