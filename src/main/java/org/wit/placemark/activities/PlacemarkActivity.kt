package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.placemark.databinding.ActivityPlacemarkBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber.Forest.i

class PlacemarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkBinding
    var placemark = PlacemarkModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Placemark Activity started")

        binding.btnAdd.setOnClickListener() {
            placemark.title = binding.placemarkTitle.text.toString()
            placemark.description = binding.placemarkDescription.text.toString()
            if (placemark.title.isNotEmpty() && placemark.description.isNotEmpty()) {
                i("Add button pressed: " + placemark.title + "; " + placemark.description)
                app.placemarks.add(placemark.copy())
                for (i in app.placemarks.indices) {
                    i("Placemark [$i]: ${this.app.placemarks[i]}")
                }
            } else {
                Snackbar
                    .make(it, "Please enter a title and description", Snackbar.LENGTH_LONG)
                    .show()
            }
       }
    }
}