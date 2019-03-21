package com.example.hangman

import kotlin.random.Random

class Game(words: Array<String>) {
    val word = words[Random.nextInt(0, words.size)]
    var guessed = BooleanArray(word.length) {false}
    var lives = 13

    fun checker(string: String, char: Char, index: Int) {
        val tmp = string.indexOf(char, index, true)

        if (tmp != -1) {
            guessed[tmp] = true
            checker(string, char, tmp + 1)
        }
        return
    }

    fun censorWord(word: String): String {
        var censoredWord = ""
        for (i in 0 until word.length) {
            if (guessed[i]) {
                censoredWord += word[i]
            } else {
                censoredWord += " _ "
            }
        }
        return censoredWord
    }
}


