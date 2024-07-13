package ru.europlast.europlasttech.data

import retrofit2.Response
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

}




