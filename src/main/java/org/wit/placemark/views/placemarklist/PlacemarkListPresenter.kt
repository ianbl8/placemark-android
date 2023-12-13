package org.wit.placemark.views.placemarklist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.placemark.views.placemark.PlacemarkView
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.views.map.MapView

class PlacemarkListPresenter(private val view: PlacemarkListView) {

    var app: MainApp = view.application as MainApp
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        registerMapCallback()
        registerRefreshCallBack()
    }

    fun getPlacemarks() = app.placemarks.findAll()

    fun doAddPlacemark() {
        val launcherIntent = Intent(view, PlacemarkView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditPlacemark(placemark: PlacemarkModel, pos: Int) {
        val launcherIntent = Intent(view, PlacemarkView::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowPlacemarksMap() {
        val launcherIntent = Intent(view, MapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallBack() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    }

}