package com.example.lab6

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.os.Looper.myLooper
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import java.util.*
import kotlin.math.roundToInt


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val PERMISSION_ID = 1000
    lateinit var latitude: TextView
    lateinit var longitude: TextView
    lateinit var latText: TextView
    lateinit var longText: TextView

    private lateinit var compass: ImageView
    private var azimuth = 0f
    private lateinit var sensorManager: SensorManager

    private var brightness: Sensor? = null
    private var temperature: String? = null

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.apply {
                val temp = getIntExtra(
                    BatteryManager.EXTRA_TEMPERATURE, 0
                ) / 10F
                temperature = "Phone Temperature\n$temp${0x00B0.toChar()}C"
            }
        }
    }

    @SuppressLint("ShortAlarm")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        supportActionBar?.hide()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        latitude = findViewById(R.id.latitude)
        longitude = findViewById(R.id.longitude)
        latText = findViewById(R.id.latText)
        longText = findViewById(R.id.longText)

        compass = findViewById(R.id.compass)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver, filter)

        showTemperature()

        var thinner = 1

        requestPermission()
        Thread {
            while (true) {
                if (thinner == 1) {
                    getLastLocation(latitude, longitude)
                    thinner = -1
                } else {
                    Thread.sleep(1000)
                    thinner += 1
                }
            }
        }.start()
    }


    @SuppressLint("SetTextI18n", "MissingPermission")
    fun getLastLocation(latitude: TextView, longitude: TextView) {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result

                    newLocationData()

                    latitude.text = formatCoords(location!!.latitude.toString())
                    longitude.text = formatCoords(location.longitude.toString())

                }
            } else {
                Toast.makeText(this, "Please Turn on Your device Location", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            requestPermission()
        }
    }


    @SuppressLint("MissingPermission")
    private fun newLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, myLooper()
        )
    }


    private val locationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            latitude.text = formatCoords(lastLocation.latitude.toString())
            longitude.text = formatCoords(lastLocation.longitude.toString())
        }
    }

    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug:", "You have the Permission")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
        sensorManager.registerListener(
            this, brightness,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    @SuppressLint("ResourceType")
    override fun onSensorChanged(event: SensorEvent?) {

        if (event.sensor?.type == Sensor.TYPE_LIGHT) {
            val light = event.values[0]

            if (isDark(light)) {
                window.decorView.setBackgroundColor(Color.BLACK)
                compass.setColorFilter(Color.argb(255, 255, 255, 255))
                longText.setTextColor(Color.parseColor("#ffffff"))
                latText.setTextColor(Color.parseColor("#ffffff"))
                longitude.setTextColor(Color.parseColor("#757575"))
                latitude.setTextColor(Color.parseColor("#757575"))
            } else {
                window.decorView.setBackgroundColor(Color.WHITE)
                compass.setColorFilter(Color.argb(255, 0, 0, 0))
                longText.setTextColor(Color.parseColor("#000000"))
                latText.setTextColor(Color.parseColor("#000000"))
            }

        } else {
            val degree = event!!.values[0].roundToInt().toFloat()

            val ra = RotateAnimation(
                azimuth,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )

            ra.duration = 210
            ra.fillAfter = true

            compass.startAnimation(ra)

            azimuth = -degree
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    fun formatCoords(str: String): String {
        val spLatitude = str.split(".")
        val latDegree = spLatitude[0]
        val min = ("0." + spLatitude[1]).toFloat() * 60.0f
        val latMin = min.toString().split(".")[0]
        val latSec =
            (("0." + min.toString().split(".")[1]).toFloat() * 60.0f).toString().split(".")[0]

        return "$latDegree°$latMin'$latSec''"
    }

    private fun isDark(brigh: Float): Boolean {
        return when (brigh.toInt()) {
            in 0..50 -> true
            else -> false
        }
    }

    private fun showTemperature() {
        var count = 100

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun run() {
                runOnUiThread {
                    count -= 1
                    val toast: Toast = Toast.makeText(
                        applicationContext, temperature,
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    val handler = Handler()
                    handler.postDelayed({ toast.cancel() }, 1000)
                }
            }
        }, 0, 10 * 1000)
    }

}
