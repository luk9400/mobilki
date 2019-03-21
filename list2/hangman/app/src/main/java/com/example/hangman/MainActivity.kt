package com.example.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
   var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        game = Game(resources.getStringArray(R.array.words))
        textView.text = (game as Game).censorWord((game as Game).word)
    }

    fun click(view: View) {
        if (inputText.text.toString() != "") {
            val char = inputText.text.first()

            if ((game as Game).word.contains(char, true)) {
                (game as Game).checker((game as Game).word, char, 0)
                textView.text = (game as Game).censorWord((game as Game).word)
            } else {
                if ((game as Game).lives > 0) {
                    (game as Game).lives--
                    val stage = 13 - (game as Game).lives
                    val id = resources.getIdentifier("hangman$stage", "drawable", applicationContext.packageName)
                    imageView.setImageResource(id)
                }
            }
        }
    }
}
