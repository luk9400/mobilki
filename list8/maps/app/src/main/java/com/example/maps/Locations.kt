package com.example.maps

import android.support.design.widget.Snackbar
import android.view.View
import com.google.firebase.firestore.DocumentReference
import java.util.ArrayList

object Locations {
    val LOCATIONS: MutableList<Location> = ArrayList()

    init {
    }

    fun addLocation(item: Location) {
        LOCATIONS.add(item)

    }

    fun clear() {
        LOCATIONS.clear()
    }

    fun getAllLocations(): List<Location> {
        return LOCATIONS
    }

    fun addLocation(item: Location, docRef: DocumentReference, view: View) {
        LOCATIONS.add(item)
        docRef.set(item)
            .addOnCompleteListener() {
                Snackbar.make(view, "New location saved", Snackbar.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Snackbar.make(view, "Couldn't create a location", Snackbar.LENGTH_SHORT).show()
            }
    }
}