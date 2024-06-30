package ru.europlast.europlasttech.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkInterface {
    @POST("auth")
    suspend fun authorization(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<Any>

    @GET("current_tasks")
    suspend fun getCurrentTasks(): Response<List<CurrentTask>>
}




