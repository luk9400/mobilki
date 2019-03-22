package com.example.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
   var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        game = Game(resources.getStringArray(R.array.words))
        // game needs !! or 'as Game' :(
        textView.text = game!!.censorWord((game as Game).word)
    }

    fun click(view: View) {
        if (inputText.text.toString() != "") {
            val char = inputText.text.first()
            val game = game!!

            if (game.word.contains(char, true)) {
                game.checker(game.word, char, 0)
                textView.text = game.censorWord(game.word)
                if (textView.text.toString() == game.word && game.lives > 0) {
                    Toast.makeText(this, "You won!", Toast.LENGTH_LONG).show()
                }
            } else {
                if (game.lives > 0) {
                    game.lives--
                    val stage = 13 - game.lives
                    val id = resources.getIdentifier("hangman$stage", "drawable", applicationContext.packageName)
                    imageView.setImageResource(id)
                    if (game.lives == 0) {
                        Toast.makeText(this, "You lost!", Toast.LENGTH_LONG).show()
                    }
                }
            }
            inputText.setText("")
        }
    }
}
