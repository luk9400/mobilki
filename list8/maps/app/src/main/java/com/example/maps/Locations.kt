package com.example.maps

import java.util.ArrayList

object Locations {
    val LOCATIONS: MutableList<Location> = ArrayList()

    init {}

    fun addLocation(item: Location) {
        LOCATIONS.add(item)

    }

    fun clear() {
        LOCATIONS.clear()
    }

    fun getAllLocations() : List<Location> {
        return LOCATIONS
    }
}