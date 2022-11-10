package com.assignment.rnt.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.assignment.rnt.di.dataStore
import com.assignment.rnt.model.Result
import com.assignment.rnt.network.RandomNumberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import timber.log.Timber

/**
 * Fetches data from remote source [RandomNumberApi], retains until data is used in local DataStore.
 */
class NumberDataSource(
    private val context: Context,
    private val api: RandomNumberApi
) {

    /**
     * Get random number from local DataStore if previously fetched, but not used, or from Remote API.
     *
     * @param forceRefresh - tries only fetch from Remote API, skip local cache from Datastore.
     */
    fun fetchNumber(forceRefresh: Boolean = false): Flow<Result<Int>> {
        return flow {
            val savedNumber = context.dataStore.data.map { preferences ->
                preferences[PREF_NUMBER]
            }.first()
            Timber.d("savedNumber: $savedNumber")
            savedNumber?.let {
                emit(Result.success(savedNumber))
                return@flow
            }

            Timber.d("loading from Network")
            emit(Result.loading())
            val result = getNumberResponse(
                request = {
                    api.getRandomNumber()
                }
            )
            Timber.d("loaded from Network: $result")
            result.data?.let { number ->
                saveNumberToLocalDataStore(number)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun clearNumberFromLocalDataStore() {
        Timber.d("clearNumberFromLocalDataStore")
        context.dataStore.edit { settings ->
            settings.remove(PREF_NUMBER)
        }
    }

    private suspend fun saveNumberToLocalDataStore(number: Int) {
        Timber.d("saveNumberToLocalDataStore: $number")
        context.dataStore.edit { settings ->
            settings[PREF_NUMBER] = number
        }
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

    companion object {
        private val PREF_NUMBER = intPreferencesKey("preferenceNumber")
    }
}