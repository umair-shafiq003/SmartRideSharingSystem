package com.example.smartride_sharingsystem

fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371e3 // Earth's radius in meters
    val φ1 = Math.toRadians(lat1)
    val φ2 = Math.toRadians(lat2)
    val Δφ = Math.toRadians(lat2 - lat1)
    val Δλ = Math.toRadians(lon2 - lon1)

    val a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
            Math.cos(φ1) * Math.cos(φ2) *
            Math.sin(Δλ / 2) * Math.sin(Δλ / 2)

    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return R * c // Distance in meters
}
