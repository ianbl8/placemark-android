package org.wit.placemark.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.wit.placemark.databinding.ActivityMapBinding
import org.wit.placemark.databinding.ContentMapBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel

class MapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityMapBinding
    private lateinit var contentBinding: ContentMapBinding
    lateinit var app: MainApp
    lateinit var presenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = MapPresenter(this)

        contentBinding = ContentMapBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            presenter.doPopuplateMap(it)
        }

    }

    fun showPlacemark(placemark: PlacemarkModel) {
        contentBinding.currentTitle.text = placemark.title
        contentBinding.currentDescription.text = placemark.description
        Picasso.get().load(placemark.image).into(contentBinding.currentImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

}