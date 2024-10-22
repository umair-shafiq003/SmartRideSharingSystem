package com.example.smartride_sharingsystem

class Ride(
    val rider: Rider,
    var assignedDriver: Driver
) {
    var distanceToDestination: Double = calculateDistance()
    var estimatedTime: Double = calculateEstimatedTime()

    fun calculateDistance(): Double {
        return haversine(
            rider.currentLocation.first, rider.currentLocation.second,
            rider.destinationLocation.first, rider.destinationLocation.second
        )
    }

    fun calculateEstimatedTime(): Double {
        val trafficFactor = (1..2).random() // Simulating traffic with random factor
        return (distanceToDestination / 1000) / trafficFactor // Time in minutes (approx)
    }
}
