package com.example.wsiobus

class Timetables {
    val c845: List<Double> = listOf(4.43, 5.29, 8.15, 12.45, 14.45, 15.45, 19.05)

    public fun get845(index: Int): Double {
        return c845.get(index)
    }

}