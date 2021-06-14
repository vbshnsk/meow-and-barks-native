package mvvm.ui


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.ColorStateList
import android.location.Location
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import com.example.meowsandbarks.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class MapFragment : Fragment(), OnMapReadyCallback {

    var gMap: GoogleMap? = null
    lateinit var locationProvider: FusedLocationProviderClient

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationProvider = LocationServices.getFusedLocationProviderClient(requireContext())

        return v
    }

    override fun onMapReady(map: GoogleMap) {
        Dexter.withContext(context)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {

                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    if (response.permissionName === Manifest.permission.ACCESS_FINE_LOCATION) {
                        when (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                            PERMISSION_GRANTED -> {
                                map.isMyLocationEnabled = true
                                gMap = map
                                locationProvider.lastLocation.addOnSuccessListener {
                                    gMap?.animateCamera(CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.fromLatLngZoom(
                                            LatLng(it.latitude, it.longitude),
                                            15F
                                        )
                                    ))
                                }
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(request: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Log.i("test", "denied")
                }

            }).check()
    }


}