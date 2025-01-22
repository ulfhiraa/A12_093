package com.example.projectakhir_pam.service

import com.example.projectakhir_pam.model.Kursus
import com.example.projectakhir_pam.model.KursusResponse
import com.example.projectakhir_pam.model.KursusResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// untuk mendefinisikan endpoint API yang digunakan oleh aplikasi untuk berkomunikasi dengan server

interface KursusService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // hubungkan dengan file .js

    @GET(".")
    suspend fun  getKursus(): KursusResponse

    @GET("{id_kursus}")
    suspend fun  getKursusById(@Path("id_kursus") id_kursus: String): KursusResponseDetail

    @POST("store")
    suspend fun insertKursus(@Body kursus: Kursus)

    @PUT("{id_kursus}")
    suspend fun updateKursus(@Path("id_kursus") id_kursus: String, @Body kursus: Kursus)

    @DELETE("{id_kursus}")
    suspend fun deleteKursus(@Path("id_kursus") id_kursus: String): Response<Void>

}