package com.example.todo

class listItem(var text: String?, var date: String?, var type: String?, var priority: Int) {
}

class priorityComparatorListItem {
    companion object : Comparator<listItem> {
        override fun compare(o1: listItem, o2: listItem): Int {
            return o1.priority - o2.priority
        }
    }
}