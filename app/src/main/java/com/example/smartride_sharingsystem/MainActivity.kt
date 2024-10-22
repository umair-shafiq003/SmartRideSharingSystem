package com.example.smartride_sharingsystem

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val drivers = mutableListOf<Driver>()
    private val riders = mutableListOf<Rider>()
    private val rides = mutableListOf<Ride>()

    private lateinit var outputTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        outputTextView = findViewById(R.id.outputTextView)

        val addDriverButton: Button = findViewById(R.id.addDriverButton)
        val requestRideButton: Button = findViewById(R.id.requestRideButton)
        val displayDriversButton: Button = findViewById(R.id.displayDriversButton)
        val displayRidersButton: Button = findViewById(R.id.displayRidersButton)
        val displayRidesButton: Button = findViewById(R.id.displayRidesButton)
        val clearOutputButton: Button = findViewById(R.id.clearOutputButton)

        addDriverButton.setOnClickListener {
            showAddDriverDialog()
        }

        requestRideButton.setOnClickListener {
            showAddRiderDialog()
        }

        displayDriversButton.setOnClickListener {
            displayDrivers()
        }

        displayRidersButton.setOnClickListener {
            displayRiders()
        }

        displayRidesButton.setOnClickListener {
            displayRides()
        }

        // Clear output button logic
        clearOutputButton.setOnClickListener {
            outputTextView.text = ""
        }
    }

    private fun showAddDriverDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_driver, null)
        val driverNameInput = dialogView.findViewById<EditText>(R.id.driverNameInput)
        val driverLocationInput = dialogView.findViewById<EditText>(R.id.driverLocationInput)
        val driverRatingInput = dialogView.findViewById<EditText>(R.id.driverRatingInput)

        AlertDialog.Builder(this)
            .setTitle("Add Driver")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = driverNameInput.text.toString()
                val location = driverLocationInput.text.toString().split(",").map { it.toDouble() }
                val rating = driverRatingInput.text.toString().toDouble()

                if (location.size == 2) {
                    val driver = Driver(name, Pair(location[0], location[1]), true, rating)
                    drivers.add(driver)
                    "Driver added: $name".also { outputTextView.text = it }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddRiderDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_request_ride, null)
        val riderNameInput = dialogView.findViewById<EditText>(R.id.riderNameInput)
        val riderCurrentLocationInput = dialogView.findViewById<EditText>(R.id.riderCurrentLocationInput)
        val riderDestinationInput = dialogView.findViewById<EditText>(R.id.riderDestinationInput)

        AlertDialog.Builder(this)
            .setTitle("Request Ride")
            .setView(dialogView)
            .setPositiveButton("Request") { _, _ ->
                val name = riderNameInput.text.toString()
                val currentLocation = riderCurrentLocationInput.text.toString().split(",").map { it.toDouble() }
                val destinationLocation = riderDestinationInput.text.toString().split(",").map { it.toDouble() }

                if (currentLocation.size == 2 && destinationLocation.size == 2) {
                    val rider = Rider(name, Pair(currentLocation[0], currentLocation[1]), Pair(destinationLocation[0], destinationLocation[1]))
                    riders.add(rider)

                    // Requesting ride and adding to rides list
                    val ride = rider.requestRide(drivers)
                    if (ride != null) {
                        rides.add(ride)
                        outputTextView.text = buildString {
                            append("Ride requested by: ${rider.name}. ")
                            append("Assigned Driver: ${ride.assignedDriver.name}, ")
                            append("Distance: ${ride.distanceToDestination / 1000} km, ")
                            append("Estimated Time: ${ride.estimatedTime} minutes.")
                        }
                    } else {
                        "No available drivers for ${rider.name}.".also { outputTextView.text = it }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun displayDrivers() {
        val driversText = StringBuilder("Drivers:\n")
        for (driver in drivers) {
            driversText.append("Name: ${driver.name}, Location: ${driver.currentLocation.first}, ${driver.currentLocation.second}\n")
        }
        outputTextView.text = driversText.toString()
    }

    private fun displayRiders() {
        val ridersText = StringBuilder("Riders:\n")
        for (rider in riders) {
            ridersText.append("Name: ${rider.name}, From: ${rider.currentLocation.first}, ${rider.currentLocation.second}\n")
        }
        outputTextView.append("\n$ridersText") // Append riders info to the existing text
    }

    private fun displayRides() {
        val ridesText = StringBuilder("Rides:\n")
        for (ride in rides) {
            ridesText.append("Rider: ${ride.rider.name}, " +
                    "Assigned Driver: ${ride.assignedDriver.name}, " +
                    "Distance: ${ride.distanceToDestination / 1000} km, " +
                    "Estimated Time: ${ride.estimatedTime} minutes\n")
        }
        outputTextView.append("\n$ridesText") // Append rides info to the existing text
    }
}
