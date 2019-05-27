package com.example.todosql

import org.json.JSONObject
import java.io.Serializable

class ListItem(var text: String?, var date: String?, var type: String?, var priority: Int) : Serializable {
    fun toJson(): JSONObject {
        return JSONObject().run {
            put("text", text)
            put("date", date)
            put("type", type)
            put("priority", priority)
        }
    }

    constructor(json: JSONObject): this(
        json.getString("text"),
        json.getString("date"),
        json.getString("type"),
        json.getString("priority").toInt()
    )
}

class PriorityComparatorListItem {
    companion object : Comparator<ListItem> {
        override fun compare(o1: ListItem, o2: ListItem): Int {
            return o1.priority - o2.priority
        }
    }
}