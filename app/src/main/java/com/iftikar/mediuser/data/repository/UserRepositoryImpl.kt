package com.iftikar.mediuser.data.repository

import android.util.Log
import com.iftikar.mediuser.data.remote.ApiOperation
import com.iftikar.mediuser.data.remote.ApiService
import com.iftikar.mediuser.domain.model.LoginResponse
import com.iftikar.mediuser.domain.model.User
import com.iftikar.mediuser.domain.repository.UserRepository
import com.iftikar.mediuser.util.PreferenceDataStore
import com.iftikar.mediuser.util.USER_ID_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService,
    private val preferenceDataStore: PreferenceDataStore) : UserRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<ApiOperation<LoginResponse>> = flow {
        try {
            val response = apiService.login(email, password)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    preferenceDataStore.saveUser(
                        key = USER_ID_KEY,
                        value = body.message
                    )
                    emit(ApiOperation.Success(body))
                } else {
                    emit(ApiOperation.Failure(NullPointerException("Response body is null")))
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Log.e("Response-Error", errorMsg)
                emit(ApiOperation.Failure(HttpException(response)))
            }
        } catch (e: IOException) {
            Log.e("Response-Error_IO", e.message ?: "Unwon")
            emit(ApiOperation.Failure(IOException("Please check your internet connection")))
        } catch (e: HttpException) {
            Log.e("Response-Error", e.message())
           // emit(ApiOperation.Failure(e))
        } catch (e: Exception) {
            Log.e("Response-Error_Nor", e.message ?: "Unwon")
            emit(ApiOperation.Failure(e))
        }
    }

    override fun getSpecificUser(userId: String): Flow<ApiOperation<User>> = flow {
        try {
            val response = apiService.getSpecificUser(userId = userId)
            if (response.isSuccessful) {
                val users = response.body()
                val user = users?.lastOrNull() ?: run {
                    emit(ApiOperation.Failure(NullPointerException("User not found")))
                    return@flow
                }
                Log.d("Repo-User", user.isApproved.toString())
                emit(ApiOperation.Success(user))
            } else {
                Log.e("User-Error", response.message())
                emit(ApiOperation.Failure(HttpException(response)))
            }
        } catch (e: Exception) {
            Log.e("User-Error_Out", e.message ?: "Unknown error")
            emit(ApiOperation.Failure(e))
        }
    }
}
