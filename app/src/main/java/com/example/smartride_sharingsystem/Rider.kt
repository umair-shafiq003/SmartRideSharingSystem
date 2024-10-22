package com.example.smartride_sharingsystem

class Rider(
    val name: String,
    val currentLocation: Pair<Double, Double>,
    var destinationLocation: Pair<Double, Double>
) {
    fun requestRide(drivers: List<Driver>): Ride? {
        // Find an available driver
        val availableDriver = drivers.firstOrNull { it.isAvailable }
        return if (availableDriver != null) {
            availableDriver.setAvailability(false) // Mark the driver as unavailable
            Ride(this, availableDriver)
        } else {
            null // No available drivers
        }
    }
}
