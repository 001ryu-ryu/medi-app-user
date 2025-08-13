package com.iftikar.mediuser.domain.repository

import com.iftikar.mediuser.data.remote.ApiOperation
import com.iftikar.mediuser.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
    suspend fun login(email: String, password: String): Flow<ApiOperation<LoginResponse>>
}