package org.wit.placemark.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import org.wit.placemark.databinding.ActivityPlacemarkMapsBinding
import org.wit.placemark.databinding.ContentPlacemarkMapsBinding

class PlacemarkMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkMapsBinding
    private lateinit var contentBinding: ContentPlacemarkMapsBinding
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacemarkMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentPlacemarkMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

    }

}