package com.bangkit.fixmyrideapp.view.maps

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.data.adapter.MapsAdapter
import com.bangkit.fixmyrideapp.data.response.NearbyItem
import com.bangkit.fixmyrideapp.data.utils.Result
import com.bangkit.fixmyrideapp.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MapsAdapter.OnNavigateClickListener {
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var adapter: MapsAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModel.SearchFoodRecipeFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        adapter = MapsAdapter()
        adapter.setOnNavigateClickListener(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getLocationNearby()

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true

    }


    private fun getLocationNearby() {
        binding.btnYourLocation.setOnClickListener {
            //check location permission
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100
                )
                return@setOnClickListener
            }
            val location = fusedLocationClient.lastLocation
            location.addOnSuccessListener {
                if (it != null){
                    val latitude = it.latitude
                    val longitude = it.longitude
                    Log.e("lokasi", "user maps act: $latitude, $longitude")
                    val radius = "200"
                    val count = "5"
                    mapsViewModel.getNearbyLocation(latitude.toString(), longitude.toString(), radius, count).observe(this) {
                        when (it) {
                            is Result.Loading -> {}
                            is Result.Error -> {
                                Log.e("Error", it.error.toString())
                            }

                            is Result.Success -> {
                                addManyMarkerLocation(it.data)
                                showMapsData(it.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addManyMarkerLocation(data: List<NearbyItem>) {
        for (i in data.indices){
            val latLngMarker = LatLng(data[i].latitude, data[i].longitude)

            mMap.addMarker(
                MarkerOptions()
                    .position(latLngMarker)
                    .icon(BitmapDescriptorFactory.fromBitmap(getmarkerBitmapFromView(data[i].name)))
                    //.title(data[i].name)
            )

            val latLngResult = LatLng(data[0].latitude, data[0].longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngResult))
            mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(
                    LatLng(
                        latLngResult.latitude,
                        latLngResult.longitude), 14f
                ))
            mMap.uiSettings.setAllGesturesEnabled(true)
            mMap.uiSettings.isZoomGesturesEnabled = true
        }

        mMap.setOnMarkerClickListener { marker ->
            val markerPosition = marker.position
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(markerPosition)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
            var markerSelected = -1
            for (i in data.indices){
                if (markerPosition.latitude == data[i].latitude && markerPosition.longitude == data[i].longitude){
                    markerSelected = i
                }
            }
            val cameraPosition = CameraPosition.Builder().target(markerPosition).zoom(14f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            adapter.notifyDataSetChanged()
            binding.rvMaps.smoothScrollToPosition(markerSelected)
            marker.showInfoWindow()
            false
        }
    }

    private fun getmarkerBitmapFromView(text: String): Bitmap{
        val customMarkerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.custom_marker, null)
        val markerTextView = customMarkerView.findViewById<TextView>(R.id.marker_title)
        markerTextView.text = text

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMarkerView.layout(0, 0, customMarkerView.measuredWidth, customMarkerView.measuredHeight)
        customMarkerView.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(customMarkerView.measuredWidth, customMarkerView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val drawable = customMarkerView.background
        if (drawable != null) {
            drawable.draw(canvas)
        }
        customMarkerView.draw(canvas)
        return returnedBitmap
    }

    private fun showMapsData(data: List<NearbyItem>){
        binding.rvMaps.adapter = adapter
        binding.rvMaps.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter.submitList(data)
        binding.rvMaps.setHasFixedSize(true)
    }

    override fun onNavigateClicked(location: NearbyItem) {
        val latitude = location.latitude
        val longitude = location.longitude
        val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null){
            startActivity(mapIntent)
        } else{
            Toast.makeText(this, "Google Maps app not found", Toast.LENGTH_SHORT).show()
        }
    }


}