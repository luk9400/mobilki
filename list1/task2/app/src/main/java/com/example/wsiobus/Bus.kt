package com.example.wsiobus

class Bus(val line : String, val time : Double, val busStop : String, val day : String)

class CompareBusObjects {
    companion object : Comparator<Bus> {
        override fun compare(a: Bus, b: Bus): Int {
            return (100.0 * (a.time - b.time)).toInt()
        }
    }
}