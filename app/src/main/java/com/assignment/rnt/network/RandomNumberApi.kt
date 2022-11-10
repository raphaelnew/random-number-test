package com.assignment.rnt.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * RandomNumber REST API
 */
@SuppressWarnings("unused")
interface RandomNumberApi {

    /**
     * Get a random number
     *
     * @param min - the minumum random number
     * @param max - the maximum random number
     * @param count - the number of random numbers to generate (Maximum of 100)
     */
    @GET(RANDOM)
    suspend fun getRandomNumber(
        @Query("min") min: Int = 0,
        @Query("max") max: Int = 100,
        @Query("count") count: Int = 1
    ): Response<List<Int>>

    companion object {
        private const val RANDOM = "randomnumber"
    }
}