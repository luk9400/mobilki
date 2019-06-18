package com.example.wsiobus

fun generateBuses(line: String, times: List<Double>, busStop: String, day: String, list: MutableList<Bus>) {
    for (i in times) {
        list.add(Bus(line, i, busStop, day))
    }
}

class Timetables {
    //last update 18.06.2019
    val c845: List<Double> = listOf(4.43, 5.29, 8.15, 12.45, 14.45, 15.45, 19.05)
    val k845: List<Double> = listOf(6.10, 7.15, 10.34, 16.34, 17.54, 18.24, 20.37, 22.42)
    val c865: List<Double> = listOf(5.13, 13.15, 17.15, 21.18)
    val k865: List<Double> = listOf(7.07, 9.21, 11.31, 15.21, 19.26, 23.17)
    val g2: List<Double> = listOf(5.56, 7.16, 8.27, 13.30, 14.45, 15.39, 16.41, 17.43, 18.34)

    var buses: MutableList<Bus> = mutableListOf()

    init {
        generateBuses("845", c845, "D. cmentarz", "weekday", buses)
        generateBuses("845", k845, "D. kościół", "weekday", buses)
        generateBuses("865", c865, "D. cmentarz", "weekday", buses)
        generateBuses("865", k865, "D. kościół", "weekday", buses)
        generateBuses("G2", g2, "D. cmentarz", "weekday", buses)
        buses.sortWith(CompareBusObjects)
    }
}
