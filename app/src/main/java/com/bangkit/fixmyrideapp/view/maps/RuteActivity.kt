package com.bangkit.fixmyrideapp.view.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.util.DirectionConverter
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.databinding.ActivityRuteBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class RuteActivity : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var binding: ActivityRuteBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        getDataFromRV()
        setupButton()
//        showDirection()
    }

    private fun setupButton() {
        val latitudeBengkel = intent.getDoubleExtra(LATITUDE, 0.0)
        val longitudeBengkel = intent.getDoubleExtra(LONGITUDE, 0.0)
        val phone_number = intent.getStringExtra(PHONE_NUMBER)
        val name = intent.getStringExtra(NAME)
        binding.llRoute.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=$latitudeBengkel,$longitudeBengkel"))
            startActivity(intent)
        }

        binding.llPhone.setOnClickListener {
            val intent: Intent
            intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone_number"))
            startActivity(intent)
        }

        binding.llShare.setOnClickListener {
            val strUri = "http://maps.google.com/maps?saddr=$latitudeBengkel,$longitudeBengkel"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, name)
            intent.putExtra(Intent.EXTRA_TEXT, strUri)
            startActivity(Intent.createChooser(intent, "Bagikan :"))
        }
    }

    private fun getDataFromRV() {
        val name = intent.getStringExtra(NAME)
        val adress = intent.getStringExtra(ADRESS)
        val rating = intent.getFloatExtra(RATING, 0.0F)
        val phone_number = intent.getStringExtra(PHONE_NUMBER)

        binding.tvNamaLokasi.text = name
        binding.tvNamaJalan.text = adress
        binding.tvRating.text = rating.toString()
        binding.ratingBar.rating = rating.toFloat()
        if (phone_number.isNullOrEmpty()){
            binding.tvDistance.text = "Tidak Ada Nomor Telefon"
        } else{
            binding.tvDistance.text = phone_number
        }
    }

//    private fun showDirection() {
//        //get latlong for polyline
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//
//        val location = fusedLocationClient.lastLocation
//        location.addOnSuccessListener {
//            var latitude = it.latitude
//            var longitude = it.longitude
//            Log.e("lokasi", "posisi kamu: $latitude, $longitude")
//            val latitudeBengkel = intent.getDoubleExtra(LATITUDE, 0.0)
//            val longitudeBengkel = intent.getDoubleExtra(LONGITUDE, 0.0)
//
//            GoogleDirection.withServerKey("AIzaSyDV8XsA79oD38uHpnZj-5avOGylIUELdWo")
//                .from(LatLng(latitude, longitude))
//                .to(LatLng(latitudeBengkel, longitudeBengkel))
//                .transportMode(TransportMode.DRIVING)
//                .execute(this)
//        }
//    }

    companion object {
        const val NAME = "name"
        const val ADRESS = "adress"
        const val RATING = "rating"
        const val PHONE_NUMBER = "phone_number"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val name = intent.getStringExtra(NAME)
        val latitudeBengkel = intent.getDoubleExtra(LATITUDE, 0.0)
        val longitudeBengkel = intent.getDoubleExtra(LONGITUDE, 0.0)
        val endPoint = LatLng(latitudeBengkel, longitudeBengkel)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val location = fusedLocationClient.lastLocation
        location.addOnSuccessListener {
            var latitude = it.latitude
            var longitude = it.longitude
            Log.e("lokasi", "user rute act: $latitude, $longitude")
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title("Posisi Anda")
            )

            mMap.addMarker(
                MarkerOptions()
                    .position(endPoint)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(name)
            )

            val polyLineOptions = PolylineOptions()
                .add(LatLng(latitude, longitude), endPoint)
                .width(5f)
                .color(Color.RED)
            mMap.addPolyline(polyLineOptions)
            mMap.isMyLocationEnabled = true
            mMap.setPadding(0,60,0,0)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 14f))
        }


    }

//    @SuppressLint("SetTextI18n")
//    override fun onDirectionSuccess(direction: Direction) {
//        if (direction.isOK){
//            val name = intent.getStringExtra(NAME)
//            val latitudeBengkel = intent.getDoubleExtra(LATITUDE, 0.0)
//            val longitudeBengkel = intent.getDoubleExtra(LONGITUDE, 0.0)
//            val route = direction.routeList[0]
//            val leg = route.legList[0]
//            val distanceInfo = leg.distance
//            val durationInfo = leg.duration
//            val strDistance = distanceInfo.text
//            val strDuration = durationInfo.text.replace("mins", "mnt")
//            binding.tvDistance.text = "Jarak lokasi tujuan dari rumah kamu $strDistance dan waktu tempuh sekitar $strDuration"
//
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                return
//            }
//
//            val location = fusedLocationClient.lastLocation
//            location.addOnSuccessListener {
//                var latitude = it.latitude
//                var longitude = it.longitude
//
//                Log.e("lokasi", "posisi kamu: $latitude, $longitude")
//
//                mMap.addMarker(MarkerOptions()
//                    .title("Lokasi Kamu")
//                    .position(LatLng(latitude,longitude))
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                )
//
//                mMap.addMarker(MarkerOptions()
//                    .title(name)
//                    .position(LatLng(latitudeBengkel, longitudeBengkel))
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//
//                )
//                mMap.animateCamera(CameraUpdateFactory
//                    .newLatLngZoom(LatLng(latitude, longitude), 14f))
//                val directionPositionList = direction.routeList[0].legList[0].directionPoint
//                mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 6, Color.RED))
//            }
//            }
//    }
//
//    override fun onDirectionFailure(t: Throwable?) {
//        Toast.makeText(this, "Oops, gagal menampilkan rute!", Toast.LENGTH_SHORT).show()
//    }
}