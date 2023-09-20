package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.placemark.databinding.ActivityPlacemarkBinding
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class PlacemarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkBinding
    val placemarks = ArrayList<PlacemarkModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Placemark activity started")

        binding.btnAdd.setOnClickListener() {
            val newPlacemark = PlacemarkModel()
            newPlacemark.title = binding.placemarkTitle.text.toString()
            if (newPlacemark.title.isNotEmpty()) {
                i("Add button pressed: " + newPlacemark.title)
                placemarks.add(newPlacemark.copy())
                i("Placemarks: $placemarks")
            } else {
                Snackbar
                    .make(it, "Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
       }
    }
}