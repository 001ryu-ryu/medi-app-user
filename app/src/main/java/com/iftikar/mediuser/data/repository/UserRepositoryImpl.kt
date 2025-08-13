package com.iftikar.mediuser.data.repository

import android.util.Log
import com.iftikar.mediuser.data.remote.ApiOperation
import com.iftikar.mediuser.data.remote.ApiService
import com.iftikar.mediuser.domain.model.LoginResponse
import com.iftikar.mediuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<ApiOperation<LoginResponse>> = flow {
        try {
            val response = apiService.login(email, password)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
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
            emit(ApiOperation.Failure(e))
        } catch (e: HttpException) {
            emit(ApiOperation.Failure(e))
        } catch (e: Exception) {
            emit(ApiOperation.Failure(e))
        }
    }
}