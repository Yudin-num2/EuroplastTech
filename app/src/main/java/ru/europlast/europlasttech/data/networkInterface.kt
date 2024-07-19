package ru.europlast.europlasttech.data

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

interface NetworkInterface {
    @POST("auth")
    suspend fun authorization(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<Any>

    @GET("current_tasks")
    suspend fun getCurrentTasks(): Response<List<CurrentTask>>

    @PATCH("current_task")
    suspend fun updateTaskStatus(
        @Query("task_id") taskId: UUID,
        @Query("status") status: String
    ): Response<Any>

    @GET("tech_card")
    suspend fun getTechCard(
        @Query("tech_card_name") techCardName: String,
    ): Response<TechCard>

    @GET("current_sockets")
    suspend fun getCurrentSockets(
        @Query("machine_name") machineName: String,
    ): Response<CurrentSocketsState>

    @PATCH("current_sockets")
    suspend fun updateSockets(
        @Query("machine_name") machineName: String,
        @Query("sockets_state") socketsState: String,
    ): Response<Any>
}


object RetrofitInstance {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl("http://10.0.3.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val networkAPI: NetworkInterface by lazy {
        retrofit.create(NetworkInterface::class.java)
    }
}




