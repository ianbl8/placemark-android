package org.wit.placemark.main

import android.app.Application
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val placemarks = ArrayList<PlacemarkModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")

        // Test placemarks
        placemarks.add(PlacemarkModel("One", "First test"))
        placemarks.add(PlacemarkModel("Two", "Second test"))
        placemarks.add(PlacemarkModel("Three", "Third test"))
    }
}