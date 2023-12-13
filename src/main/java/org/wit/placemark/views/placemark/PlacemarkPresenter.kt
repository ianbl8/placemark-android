package org.wit.placemark.views.placemark

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.views.location.LocationView
import timber.log.Timber.Forest.i

class PlacemarkPresenter(private val view: PlacemarkView) {

    var placemark = PlacemarkModel()
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var edit = false

    init {
        if (view.intent.hasExtra("placemark_edit")) {
            edit = true
            @Suppress("DEPRECATION")
            placemark = view.intent.extras?.getParcelable("placemark_edit")!!
            view.showPlacemark(placemark)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String) {
        placemark.title = title
        placemark.description = description
        if (edit) {
            app.placemarks.update(placemark)
        } else {
            app.placemarks.create(placemark)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.placemarks.delete(placemark)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher, view)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.129102, 15f)
        if (placemark.zoom != 0f) {
            location.lat = placemark.lat
            location.lng = placemark.lng
            location.zoom = placemark.zoom
        }
        val launcherIntent = Intent(view, LocationView::class.java).putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cachePlacemark(title: String, description: String) {
        placemark.title = title
        placemark.description = description
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            i("Got result ${result.data!!.data}")
                            placemark.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(
                                placemark.image, Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            view.updateImage(placemark.image)
                        }
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            i("Got location ${result.data.toString()}")
                            @Suppress("DEPRECATION")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            placemark.lat = location.lat
                            placemark.lng = location.lng
                            placemark.zoom = location.zoom
                        }
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}