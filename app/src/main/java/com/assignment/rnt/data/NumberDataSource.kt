package com.assignment.rnt.data

import com.assignment.rnt.model.Result
import com.assignment.rnt.network.RandomNumberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber

/**
 * Fetches data from remote source [RandomNumberApi], retains until data is used in local DataStore.
 */
class NumberDataSource(private val api: RandomNumberApi) {

    /**
     * Get random number from local DataStore if previously fetched, but not used, or from Remote API.
     *
     * @param forceRefresh - tries only fetch from Remote API, skip local cache from Datastore.
     */
    fun fetchNumber(forceRefresh: Boolean = false): Flow<Result<Int>> {
        //todo local caching in Datastore.
        return flow {
            emit(Result.loading())
            val result = getNumberResponse(
                request = {
                    api.getRandomNumber()
                }
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getNumberResponse(
        request: suspend () -> Response<List<Int>>
    ): Result<Int> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                val rawValue = result.body()
                return rawValue?.firstOrNull()?.let { Result.success(it) }
                    ?: Result.error("No data", null)
            } else {
                Result.error(result.message(), null)
            }
        } catch (e: Throwable) {
            Timber.e(e)
            Result.error("Unknown Error", null)
        }
    }
}