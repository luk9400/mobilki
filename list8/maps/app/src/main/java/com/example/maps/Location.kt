package com.example.maps

data class Location(val id: String? = "", val name: String? = "", val longitude: Double? = 0.0, val latitude: Double? = 0.0) {
    override fun toString(): String {
        return name!!
    }
}