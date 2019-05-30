package com.example.tictaconline.datamodel

data class Board(val id: String = "", val user1: String = "", val user2: String = "", val turn: String = "", val board: MutableList<String> = arrayListOf())
