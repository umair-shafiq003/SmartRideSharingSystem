package com.example.smartride_sharingsystem

class Driver(
    val name: String,
    var currentLocation: Pair<Double, Double>,
    var isAvailable: Boolean,
    var rating: Double
) {
    fun setAvailability(available: Boolean) {
        this.isAvailable = available
    }

    fun updateRating(newRating: Double) {
        rating = (rating + newRating) / 2
    }
}
