package org.wit.placemark.models

import timber.log.Timber.Forest.i

class PlacemarkMemStore: PlacemarkStore {

    val placemarks = ArrayList<PlacemarkModel>()

    override fun findAll(): List<PlacemarkModel> {
        return placemarks
    }

    override fun create(placemark: PlacemarkModel) {
        placemarks.add(placemark)
        logAll()
    }

    fun logAll() {
        placemarks.forEach{ i("${it}") }
    }

}