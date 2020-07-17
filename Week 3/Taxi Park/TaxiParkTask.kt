package taxipark

import kotlin.math.min

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
    val driversThatHasTrips = trips.map(Trip::driver)
    return allDrivers.filterNot(driversThatHasTrips::contains).toSet()
}

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> = if (minTrips == 0) allPassengers else trips
        .flatMap(Trip::passengers)
        .groupBy { it }
        .filter { it.value.size >= minTrips }
        .flatMap { it.value }
        .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> = trips
        .filter { it.driver == driver }
        .flatMap(Trip::passengers)
        .groupBy { it }
        .filter { (_, value) -> value.size > 1 }
        .flatMap { it.value }
        .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> = trips
        .flatMap { trip ->
            trip.passengers.map {
                it to trip
            }
        }
        .groupBy { it.first }
        .filter { (_, value) ->
            val tripsWithDiscount = value.filter { (_, trip) -> trip.discount != null && trip.discount > 0.0 }
            value.size - tripsWithDiscount.size < tripsWithDiscount.size
        }
        .map { it.key }
        .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty()) return null
    return trips.map {
        val from = it.duration - (it.duration % 10)
        val to = it.duration + 9 - (it.duration % 10)
        from..to
    }.groupBy { it }.maxBy { it.value.size }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val allIncomeFromDrivers = trips.groupBy(Trip::driver).mapValues { (_, value) ->
        value.sumByDouble(Trip::cost)
    }

    val sumOf20PercentageDrivers = allIncomeFromDrivers.toList()
            .map { it.second }
            .sortedByDescending { it }
            .chunked((allDrivers.count() * 0.2).toInt())
            .firstOrNull()?.sum() ?: 0.0

    val sumOfAllDrivers = allIncomeFromDrivers.map { it.value }.sum()

    return sumOf20PercentageDrivers >= sumOfAllDrivers * 0.8
}