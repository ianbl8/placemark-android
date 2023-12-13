package org.wit.placemark.activities

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.placemark.main.MainApp

class MapPresenter(val view: MapView) {

    var app: MainApp = view.application as MainApp

    fun doPopuplateMap(map: GoogleMap) {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(view)
        app.placemarks.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).snippet(it.description).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val placemark = app.placemarks.findById(tag)
        if (placemark != null) {
            view.showPlacemark(placemark)
        }

    }

}