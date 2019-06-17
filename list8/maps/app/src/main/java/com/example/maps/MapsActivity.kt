package com.example.maps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private lateinit var mMap: GoogleMap
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()
        populateSpinner()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun populateSpinner() {
        db.collection("locations")
            .get()
            .addOnSuccessListener { documments ->
                Locations.clear()
                for (document in documments) {
                    val n = document.toObject(Location::class.java)
                    Locations.addLocation(n)
                }
            }

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Locations.getAllLocations())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@MapsActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
                Log.d("dupa", Locations.getAllLocations()[position].name)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                Log.d("dupa", "nic")
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Toast.makeText(this@MapsActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        Log.d("dupa", Locations.getAllLocations()[position].name)

        val long = (parent.getItemAtPosition(position) as Location).longitude
        val lat = (parent.getItemAtPosition(position) as Location).latitude
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat!!, long!!)))
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.d("dupa", "nic1")
    }

}
