package com.example.wsiobus

fun generateBuses(line: String, times: List<Double>, busStop: String, day: String, list: MutableList<Bus>) {
    for (i in times) {
        list.add(Bus(line, i, busStop, day))
    }
}

class Timetables {
    val c845: List<Double> = listOf(4.43, 5.29, 8.15, 12.45, 14.45, 15.45, 19.05)
    val k845: List<Double> = listOf(6.10, 7.15, 10.14, 16.34, 17.54, 18.24, 20.37, 22.32)
    val c865: List<Double> = listOf(5.15, 13.14, 17.17, 21.17)
    val k865: List<Double> = listOf(7.24, 9.13, 11.13, 15.22, 19.21, 23.14)
    val g2: List<Double> = listOf(5.15, 6.02, 6.49, 8.11, 16.08, 17.06, 17.40, 18.44)

    var buses: MutableList<Bus> = mutableListOf()

    init {
        generateBuses("845", c845, "D. cmentarz", "weekday", buses)
        generateBuses("845", k845, "D. kosciol", "weekday", buses)
        generateBuses("865", c865, "D. cmentarz", "weekday", buses)
        generateBuses("865", k865, "D. kosciol", "weekday", buses)
        generateBuses("G2", g2, "D. cmentarz", "weekday", buses)
        buses.sortWith(CompareObjects)
    }
}